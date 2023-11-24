package com.example.deliciouscard.service;

import com.example.deliciouscard.dto.RestaurantResponseDto;
import com.example.deliciouscard.entity.Restaurant;
import com.example.deliciouscard.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return new RestaurantResponseDto(restaurant);
    }

    public List<RestaurantResponseDto> getRestaurantList() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<RestaurantResponseDto> restaurantResponseDto = new ArrayList<>();

        restaurantList.forEach(restaurant -> restaurantResponseDto.add(new RestaurantResponseDto(restaurant)));

        return restaurantResponseDto;
    }
}
