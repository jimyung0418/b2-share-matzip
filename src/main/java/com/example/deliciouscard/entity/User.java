package com.example.deliciouscard.entity;

import com.example.deliciouscard.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Table
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentLikes> commentlikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PostLikes> postlikes = new ArrayList<>();


    public User(UserRequestDto userRequestDto, String encodedPassword) {
        this.username = userRequestDto.getUsername();
        this.password = encodedPassword;
        this.introduction = userRequestDto.getIntroduction();
        this.nickname = userRequestDto.getNickname();
        this.gender = userRequestDto.getGender();
        this.age = userRequestDto.getAge();
        this.address = userRequestDto.getAddress();
        this.email = userRequestDto.getEmail();
    }

    public User update(UserRequestDto userRequestDto,String encodedPassword) {
        this.username = userRequestDto.getUsername()== null? this.username : userRequestDto.getUsername();
        this.password = Objects.equals(encodedPassword, "") ? this.password : encodedPassword;
        this.introduction = userRequestDto.getIntroduction()== null? this.introduction : userRequestDto.getIntroduction();
        this.nickname = userRequestDto.getNickname()== null? this.nickname : userRequestDto.getNickname();
        this.gender = userRequestDto.getGender()== null? this.gender : userRequestDto.getGender();
        this.age = userRequestDto.getAge()== null? this.age : userRequestDto.getAge();
        this.address = userRequestDto.getAddress()== null? this.address : userRequestDto.getAddress();
        this.email = userRequestDto.getEmail()== null? this.email : userRequestDto.getEmail();
        return this;
    }
}
