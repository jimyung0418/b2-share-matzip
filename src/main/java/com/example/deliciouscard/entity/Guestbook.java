package com.example.deliciouscard.entity;

import com.example.deliciouscard.dto.GuestbookRequestDto;
import com.example.deliciouscard.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Guestbook extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean confirm;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String author;

    public Guestbook(User user, GuestbookRequestDto guestbookRequestDto, UserDetailsImpl userDetails) {
        this.content = guestbookRequestDto.getContent();
        this.user = user;
        this.confirm = false;
        this.author = userDetails.getUser().getUsername();
    }

    public void updateGuestbook(GuestbookRequestDto guestbookRequestDto) {
        this.content = guestbookRequestDto.getContent();
    }

    public void checkGuestbook() {
        this.confirm = true;
    }
}