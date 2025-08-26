package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.powerup.infrastructure.out.feign.client.PlazaFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.request.RestaurantEmployeeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantEmployeeFeignAdapter implements IRestaurantEmployeePersistencePort {

    private final PlazaFeignClient plazaFeignClient;

    @Override
    public void assignEmployeeToRestaurant(int userId, int restaurantId, boolean active) {
        RestaurantEmployeeRequestDto requestDto = new RestaurantEmployeeRequestDto(userId, restaurantId,active);
        plazaFeignClient.assignEmployee(requestDto);
    }

    @Override
    public boolean getOwnership(int restaurantId, int ownerId) {
        return plazaFeignClient.getOwnership(restaurantId,ownerId);
    }
}
