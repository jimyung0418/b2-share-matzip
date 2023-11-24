package com.example.deliciouscard.entity;

import com.example.deliciouscard.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;


    public CommentLikes(Comment comment, UserDetailsImpl user) {
        this.comment = comment;
        this.user = user.getUser();
    }
}
