package com.example.deliciouscard.entity;

import com.example.deliciouscard.dto.PostRequestDto;
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
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetailsImpl) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.user = userDetailsImpl.getUser();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle()==null ? this.title : requestDto.getTitle();
        this.content = requestDto.getContent()==null ? this.content : requestDto.getContent();
    }
}
