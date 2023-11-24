package com.example.deliciouscard.controller;

import com.example.deliciouscard.dto.CommonResponseDto;
import com.example.deliciouscard.dto.GuestbookRequestDto;
import com.example.deliciouscard.dto.GuestbookResponseDto;
import com.example.deliciouscard.security.UserDetailsImpl;
import com.example.deliciouscard.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("api/users")
@RestController
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService guestbookService;

    @PostMapping("/{id}/guestbooks")
    public ResponseEntity<CommonResponseDto> createGuestbook(@PathVariable Long id, @RequestBody GuestbookRequestDto guestbookRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            guestbookService.createGuestbook(id, guestbookRequestDto, userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("방명록 등록 완료", HttpStatus.OK.value()));
    }

    @GetMapping("/{id}/guestbooks")
    public List<GuestbookResponseDto> getGuestbookList(@PathVariable Long id) {
        return guestbookService.getGuestbookList(id);
    }
}
