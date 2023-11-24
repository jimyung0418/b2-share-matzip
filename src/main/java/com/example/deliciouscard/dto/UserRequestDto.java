package com.example.deliciouscard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String username;
    private String password;
    private String newPassword;
    private String introduction;
    private String nickname;
    private String gender;
    private Integer age;
    private String address;
    private String email;
}
