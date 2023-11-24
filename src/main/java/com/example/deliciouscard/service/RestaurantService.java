package com.example.deliciouscard.service;

import com.example.deliciouscard.dto.RestaurantResponseDto;
import com.example.deliciouscard.entity.Restaurant;
import com.example.deliciouscard.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantResponseDto getRestaurant(String restaurantName) {
        Restaurant restaurant = restaurantRepository.findByrestaurantName(restaurantName);
        if (restaurant == null) {
            throw new IllegalArgumentException("해당 name 맛집이 없습니다.");
        }
        log.info(restaurant.getRestaurantName());
        log.info(restaurant.getCity());
        log.info(String.valueOf(restaurant.getLikes()));
        return new RestaurantResponseDto(restaurant);
    }
}
