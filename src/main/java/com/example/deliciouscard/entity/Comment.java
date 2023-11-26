package com.example.deliciouscard.entity;

import com.example.deliciouscard.dto.CommentRequesDto;
import com.example.deliciouscard.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "comment")
    List<CommentLikes> likes = new ArrayList<>();

    public Comment(Post post, CommentRequesDto req, UserDetailsImpl userDetails) {
        this.content = req.getContent();
        this.user = userDetails.getUser();
        this.post = post;

    }

    public void update(CommentRequesDto req) {
        this.content = req.getContent();
    }

}
