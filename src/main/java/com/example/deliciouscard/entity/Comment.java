package com.example.deliciouscard.entity;

import com.example.deliciouscard.dto.CommentRequesDto;
import com.example.deliciouscard.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cotent;

    @Column
    private Long likes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Post post, CommentRequesDto req, UserDetailsImpl userDetails) {
        this.cotent = req.getContent();
        this.user = userDetails.getUser();
        this.post = post;
    }
}
