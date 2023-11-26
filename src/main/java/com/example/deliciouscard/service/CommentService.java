package com.example.deliciouscard.service;

import com.example.deliciouscard.dto.CommentRequesDto;
import com.example.deliciouscard.dto.CommentResponseDto;
import com.example.deliciouscard.entity.Comment;
import com.example.deliciouscard.entity.CommentLikes;
import com.example.deliciouscard.entity.Post;
import com.example.deliciouscard.entity.PostLikes;
import com.example.deliciouscard.repository.CommentLikesRepository;
import com.example.deliciouscard.repository.CommentRepository;
import com.example.deliciouscard.repository.PostRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentLikesRepository commentLikesRepository;

    public CommentResponseDto createComment(Long id, CommentRequesDto req, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        if(req.getContent() == null){
            throw new IllegalArgumentException("내용을 입력하세요");
        }
        Comment comment = new Comment(post,req,userDetails);
        Comment saveComment = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);
        return commentResponseDto;
    }

    public List<CommentResponseDto> getComments() {
        List<Comment> commentList= commentRepository.findAll();
        List<CommentResponseDto> commentsList = new ArrayList<>();
        for(int i=0;i<commentList.size();i++){
            commentsList.add(new CommentResponseDto(commentList.get(i)));
        }
    return commentsList;
    }

    @Transactional
    public void modifyComment(Long id, Long commentId, CommentRequesDto req, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException(("해당 댓글이 없습니다.")));
        if(!Objects.equals(comment.getUser().getId(), user.getUser().getId())){
            throw new IllegalArgumentException("댓글 작성자만 수정 가능합니다");
        }
        comment.update(req);
    }
    public void deleteComment(Long id, Long commentId, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException(("해당 댓글이 없습니다.")));
        if(!Objects.equals(comment.getUser().getId(), user.getUser().getId())){
            throw new IllegalArgumentException("댓글 작성자만 삭제 가능합니다");
        }
        commentRepository.delete(comment);
    }


    public void uplikes(Long id, Long commentId, UserDetailsImpl user) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException(("해당 게시글이 없습니다.")));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException(("해당 댓글이 없습니다.")));
        if(Objects.equals(comment.getUser().getId(), user.getUser().getId())){
            throw new IllegalArgumentException("댓글 작성자가 좋아요를 누를 수 없습니다.");
        }
        CommentLikes likes = new CommentLikes(comment,user);
        List<CommentLikes> commentLikesList = commentLikesRepository.findAllByComment(comment);
        for(CommentLikes c:commentLikesList){
            if(Objects.equals(c.getUser().getId(), user.getUser().getId())){
                CommentLikes commentLikes =commentLikesRepository.findByCommentAndUser(likes.getComment(), likes.getUser());
                commentLikesRepository.delete(commentLikes);
                return;
            }
        }
        commentLikesRepository.save(likes);
    }
}
