package com.example.deliciouscard.entity;

import com.example.deliciouscard.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String city;

    @Column
    private Long likes;

    @OneToMany(mappedBy = "restaurant")
    private List<Post> postList = new ArrayList<>();

    public Restaurant(PostRequestDto postRequestDto) {
        this.restaurantName = postRequestDto.getRestaurantName();
        this.city = postRequestDto.getCity();
        this.likes = 0L;
    }
}
