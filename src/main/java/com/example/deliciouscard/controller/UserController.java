package com.example.deliciouscard.controller;

import com.example.deliciouscard.dto.CommonResponseDto;
import com.example.deliciouscard.dto.UserRequestDto;
import com.example.deliciouscard.dto.UserResponseDto;
import com.example.deliciouscard.jwt.JwtUtil;
import com.example.deliciouscard.security.UserDetailsImpl;
import com.example.deliciouscard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody UserRequestDto userRequestDto){
        try {
            userService.signup(userRequestDto);
            return ResponseEntity.ok().body(new CommonResponseDto("회원가입완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        try {
            userService.login(userRequestDto);
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDto.getUsername()));
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 완료",HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto> getProfile(@PathVariable Long id){
        try {
            UserResponseDto userResponseDto = userService.getProfile(id);
            return ResponseEntity.ok().body(userResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponseDto> updateProfile(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        try {
            UserResponseDto userResponseDto = userService.updateProfile(id,userRequestDto,userDetailsImpl);
            return ResponseEntity.ok().body(userResponseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }
}
