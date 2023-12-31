package com.example.deliciouscard.service;

import com.example.deliciouscard.dto.PostRequestDto;
import com.example.deliciouscard.dto.PostResponseDto;
import com.example.deliciouscard.entity.CommentLikes;
import com.example.deliciouscard.entity.Post;
import com.example.deliciouscard.entity.PostLikes;
import com.example.deliciouscard.entity.Restaurant;
import com.example.deliciouscard.repository.PostLikesRepository;
import com.example.deliciouscard.repository.PostRepository;
import com.example.deliciouscard.repository.RestaurantRepository;
import com.example.deliciouscard.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final RestaurantRepository restaurantRepository;

    public PostResponseDto createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetailsImpl) {
        // 이곳으로 넘어왔다는 것은 로그인한 유저 정보가 잘 넘어왔다는 것


        // 예외 처리
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목을 입력해야 합니다.");
        } else if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력해야 합니다.");
        } else if(postRequestDto.getRestaurantName() ==null){
            throw new IllegalArgumentException("음식점 이름을 입력해야 합니다.");
        } else if (postRequestDto.getCity() == null){
            throw new IllegalArgumentException("주소지(시)를 입력해야 합니다.");
        }
        // postRequestDto 로 들어온 restaurant 정보로 DB에 있는지 확인하기
        String restaurantName = postRequestDto.getRestaurantName();
        String city = postRequestDto.getCity();
        Restaurant findRestaurant = restaurantRepository.findByRestaurantNameAndCity(restaurantName, city);
        // restaurant DB에 없는 경우 요청한 값으로 저장을 해준다.
        if (findRestaurant == null) {
            findRestaurant = new Restaurant((postRequestDto));
            restaurantRepository.save(findRestaurant);
        }

        // 작성한 post 정보로 Post 객체 만들기
        Post post = new Post(postRequestDto,userDetailsImpl, findRestaurant);
        // Post DB에 저장
        Post savePost = postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }

    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> response = new ArrayList<>();
        for(int i = 0; i< postList.size();i++){
                response.add(new PostResponseDto(postList.get(i)));
        }
        return response;
    }

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id 게시물이 없습니다."));
        return new PostResponseDto(post);
    }

    @Transactional
    public void updatePost(Long id, PostRequestDto requestDto, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id 게시물이 없습니다."));
        if(!Objects.equals(post.getUser().getId(), user.getUser().getId())){
            throw new IllegalArgumentException("게시물 작성자만 수정 가능합니다");
        }
        post.update(requestDto);
    }

    public void deletePost(Long id, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id 게시물이 없습니다."));
        if(!Objects.equals(post.getUser().getId(), user.getUser().getId())){
            throw new IllegalArgumentException("게시물 작성자만 수정 가능합니다");
        }
        postRepository.delete(post);
    }
    public void uplikes(Long id, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        if(Objects.equals(post.getUser().getId(), user.getUser().getId())){
            throw new IllegalArgumentException("게시글 작성자가 좋아요를 누를 수 없습니다.");
        }
        PostLikes likes = new PostLikes(post,user);
        List<PostLikes> postLikesList = postLikesRepository.findAllByPost(post);
        for(PostLikes p:postLikesList){
            if(Objects.equals(p.getUser().getId(), user.getUser().getId())){
                PostLikes postLikes =postLikesRepository.findByPostAndUser(likes.getPost(), likes.getUser());
                postLikesRepository.delete(postLikes);
                return;
            }
        }
        postLikesRepository.save(likes);
    }
}
