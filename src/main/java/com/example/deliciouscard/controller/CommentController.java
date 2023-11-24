package com.example.deliciouscard.controller;

import com.example.deliciouscard.dto.CommentRequesDto;
import com.example.deliciouscard.dto.CommentResponseDto;
import com.example.deliciouscard.dto.CommonResponseDto;
import com.example.deliciouscard.security.UserDetailsImpl;
import com.example.deliciouscard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommonResponseDto> postComment(@PathVariable Long id,@RequestBody CommentRequesDto req,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommentResponseDto commentRes = commentService.createComment(id,req,userDetails);
            return ResponseEntity.ok().body(commentRes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    @GetMapping("/{id}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long id){
        return commentService.getComments();
    }
    @PatchMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> modifyComment(@PathVariable Long id,@PathVariable Long commentId, @RequestBody CommentRequesDto req,@AuthenticationPrincipal UserDetailsImpl user){
        try {
            commentService.modifyComment(id,commentId,req,user);
            return ResponseEntity.ok().body(new CommonResponseDto("수정완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long id,@PathVariable Long commentId,@AuthenticationPrincipal UserDetailsImpl user){
        try {
            commentService.deleteComment(id,commentId,user);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제완료", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    @PostMapping("/{id}/comments/{commentId}")
    public ResponseEntity<CommonResponseDto> commentLikes(@PathVariable Long id,@PathVariable Long commentId,@AuthenticationPrincipal UserDetailsImpl user){
        try {
            commentService.uplikes(id,commentId,user);
            return ResponseEntity.ok().body(new CommonResponseDto("좋아요!+1", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
