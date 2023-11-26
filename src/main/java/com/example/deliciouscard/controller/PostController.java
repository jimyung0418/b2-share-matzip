package com.example.deliciouscard.controller;

import com.example.deliciouscard.dto.CommonResponseDto;
import com.example.deliciouscard.dto.PostRequestDto;
import com.example.deliciouscard.dto.PostResponseDto;
import com.example.deliciouscard.security.UserDetailsImpl;
import com.example.deliciouscard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        try {
            PostResponseDto postResponseDto = postService.createPost(postRequestDto,userDetailsImpl);
            return ResponseEntity.ok().body(postResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    @GetMapping
    public List<PostResponseDto> getPostList(){
        return postService.getPostList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto> getPost(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(postService.getPost(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl user){
        try {
            postService.updatePost(id,requestDto,user);
            return ResponseEntity.ok().body(new CommonResponseDto("수정완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponseDto>deletePost(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl user){
        try {
            postService.deletePost(id,user);
            return ResponseEntity.ok().body(new CommonResponseDto("삭제완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommonResponseDto> commentLikes(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl user){
        try {
            postService.uplikes(id,user);
            return ResponseEntity.ok().body(new CommonResponseDto("좋아요!on/off", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
