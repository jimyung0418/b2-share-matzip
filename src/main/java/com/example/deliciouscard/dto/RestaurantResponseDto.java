package com.example.deliciouscard.dto;

import com.example.deliciouscard.entity.Restaurant;
import lombok.Getter;

@Getter
public class RestaurantResponseDto extends CommonResponseDto{
    private String restaurantName;
    private String city;
    private Long likes;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.restaurantName = restaurant.getRestaurantName();
        this.city = restaurant.getCity();
        this.likes = restaurant.getLikes();
    }
}
