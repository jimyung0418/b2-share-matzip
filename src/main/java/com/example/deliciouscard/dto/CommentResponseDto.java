package com.example.deliciouscard.dto;


import com.example.deliciouscard.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto extends CommonResponseDto{
    private String Content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long likes;

    public CommentResponseDto(Comment comment) {
        this.Content = comment.getCotent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likes = (long) comment.getLikes().size();
    }
}
