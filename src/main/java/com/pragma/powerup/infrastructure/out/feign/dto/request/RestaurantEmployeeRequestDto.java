package com.pragma.powerup.infrastructure.out.feign.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEmployeeRequestDto {
    private int userId;
    private int restaurantId;
    private boolean active;
}
