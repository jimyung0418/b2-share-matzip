package com.example.deliciouscard.service;

import com.example.deliciouscard.dto.CommentRequesDto;
import com.example.deliciouscard.dto.CommentResponseDto;
import com.example.deliciouscard.entity.Comment;
import com.example.deliciouscard.entity.Post;
import com.example.deliciouscard.repository.CommentRepository;
import com.example.deliciouscard.repository.PostRepository;
import com.example.deliciouscard.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

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
}
