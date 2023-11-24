package com.example.deliciouscard.dto;

import com.example.deliciouscard.entity.Guestbook;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GuestbookResponseDto extends CommonResponseDto{
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.content = guestbook.getContent();
        this.author = guestbook.getAuthor();
        this.createdAt = guestbook.getCreatedAt();
        this.updatedAt = guestbook.getModifiedAt();
    }
}
