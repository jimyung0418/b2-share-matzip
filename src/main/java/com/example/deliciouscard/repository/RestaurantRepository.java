package com.example.deliciouscard.repository;

import com.example.deliciouscard.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantNameAndCity(String restaurantName, String city);

    Restaurant findByrestaurantName(String restaurantName);
}
