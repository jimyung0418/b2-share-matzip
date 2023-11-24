package com.example.deliciouscard.service;

import com.example.deliciouscard.dto.UserRequestDto;
import com.example.deliciouscard.dto.UserResponseDto;
import com.example.deliciouscard.entity.User;
import com.example.deliciouscard.repository.UserRepository;
import com.example.deliciouscard.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    public void signup(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            log.info("이미 존재하는 유저명입니다.");
            throw new IllegalArgumentException("이미 존재하는 유저명입니다.");
        }
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto,encodedPassword);
        userRepository.save(user);
    }

    public void login(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();
        //db에 이름 여부 파악
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("해당이름이 없습니다."));
        //비밀번호 일치
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.info("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public UserResponseDto getProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id의 정보가 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateProfile(Long id, UserRequestDto userRequestDto, UserDetailsImpl userDetailsImpl) {
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id의 정보가 없습니다."));
        if(!(userRequestDto.getPassword() == null)) {
            if (!passwordEncoder.matches(userRequestDto.getPassword(), userDetailsImpl.getPassword())) {
                log.info("비밀번호가 일치하지 않습니다.");
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        }
        String encodedPassword = "";
        if (userRequestDto.getNewPassword() != null) {
            encodedPassword = passwordEncoder.encode(userRequestDto.getNewPassword());
        }
        User updatedUser = user.update(userRequestDto,encodedPassword);

        return new UserResponseDto(updatedUser);
    }
}
