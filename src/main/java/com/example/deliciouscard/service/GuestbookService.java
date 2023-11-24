package com.example.deliciouscard.service;

import com.example.deliciouscard.dto.GuestbookRequestDto;
import com.example.deliciouscard.dto.GuestbookResponseDto;
import com.example.deliciouscard.entity.Guestbook;
import com.example.deliciouscard.entity.User;
import com.example.deliciouscard.repository.GuestbookRepository;
import com.example.deliciouscard.repository.UserRepository;
import com.example.deliciouscard.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;
    private final UserRepository userRepository;

    public void createGuestbook(Long id, GuestbookRequestDto guestbookRequestDto, UserDetailsImpl userDetails) {
        // 해당 프로필이 존재하는지 확인
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id의 정보가 없습니다."));


        // 방명록 내용입력 확인
        if (guestbookRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력하세요");
        }

        // 방명록 등록
        Guestbook guestbook = new Guestbook(user, guestbookRequestDto, userDetails);
        guestbookRepository.save(guestbook);
    }

    public List<GuestbookResponseDto> getGuestbookList(Long id) {
        // 해당 id의 프로필이 존재하는지 확인
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id의 정보가 없습니다."));

        // 방명록 레포지토리를 통해 해당 프로필의 방명록 모두 가져오기
        List<Guestbook> guestbookList = guestbookRepository.findAllByUser(user);

        // DTO로 변환 후 DTO리스트에 담기
        List<GuestbookResponseDto> responseDto = new ArrayList<>();
        for (Guestbook guestbook : guestbookList) {
            responseDto.add(new GuestbookResponseDto(guestbook));
        }

        // 변환된 DTO 반환
        return responseDto;
    }
}
