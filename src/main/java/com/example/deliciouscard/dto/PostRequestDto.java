package com.example.deliciouscard.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private String restaurantName;
    private String city;
}
