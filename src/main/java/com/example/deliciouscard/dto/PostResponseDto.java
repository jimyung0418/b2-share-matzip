package com.example.deliciouscard.dto;

import com.example.deliciouscard.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto extends CommonResponseDto{
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickName;
    private String restaurantName;
    private String city;
    private Long likes;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.nickName = post.getUser().getNickname();
        this.restaurantName = post.getRestaurant().getRestaurantName();
        this.city = post.getRestaurant().getCity();
        this.likes = (long) post.getLikes().size();
    }
}
