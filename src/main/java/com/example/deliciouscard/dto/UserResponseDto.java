package com.example.deliciouscard.dto;

import com.example.deliciouscard.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto extends CommonResponseDto{
    private String username;
    private String introduction;
    private String nickname;
    private String gender;
    private Integer age;
    private String address;
    private String email;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.address = user.getAddress();
        this.email = user.getEmail();
    }
}
