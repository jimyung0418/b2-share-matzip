package com.example.deliciouscard.controller;

import com.example.deliciouscard.dto.CommonResponseDto;
import com.example.deliciouscard.dto.RestaurantResponseDto;
import com.example.deliciouscard.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/params")
    public ResponseEntity<CommonResponseDto> getRestaurant (@RequestParam String restaurantName) {
        try {
            RestaurantResponseDto restaurantResponseDto = restaurantService.getRestaurant(restaurantName);
            return ResponseEntity.ok().body(restaurantResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
