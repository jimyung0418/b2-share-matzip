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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Transactional
    public void patchGuestbook(Long userId, Long guestbookId, GuestbookRequestDto guestbookRequestDto, UserDetailsImpl userDetails) {
        Guestbook guestbook = checkProfileAndGuestbook(userId, guestbookId, userDetails);
        guestbook.updateGuestbook(guestbookRequestDto);
    }

    public void deleteGuestbook(Long userId, Long guestbookid, UserDetailsImpl userDetails) {
        Guestbook guestbook = checkProfileAndGuestbook(userId, guestbookid, userDetails);
        guestbookRepository.delete(guestbook);
    }

    @Transactional
    public void confirmGuestbook(Long userId, Long guestbookId, UserDetailsImpl userDetails) {
        // 해당 프로필이 존재하는지 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 id의 정보가 없습니다."));
        // 프로필 본인이 맞는지 확인
        if (!Objects.equals(userId ,userDetails.getUser().getId())) {
            throw new IllegalArgumentException("본인만 확인 가능합니다.");
        }
        Guestbook guestbook = guestbookRepository.findById(guestbookId).orElseThrow(() -> new IllegalArgumentException("해당 id의 방명록이 없습니다."));

        // 방명록 확인 완료
        guestbook.checkGuestbook();
    }

    private Guestbook checkProfileAndGuestbook(Long userId, Long guestbookId, UserDetailsImpl userDetails) {
        // 해당 프로필이 존재하는지 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 id의 정보가 없습니다."));

        // 해당 id의 방명록이 존재하는지 확인
        Guestbook guestbook = guestbookRepository.findById(guestbookId).orElseThrow(() -> new IllegalArgumentException("해당 id 방명록이 없습니다."));

        // 해당 방명록의 작성자와 일치하는지 확인
        if (!Objects.equals(guestbook.getAuthor(), userDetails.getUser().getUsername())) {
            throw new IllegalArgumentException("방명록 작성자만 수정 가능합니다.");
        }
        return guestbook;
    }
}
