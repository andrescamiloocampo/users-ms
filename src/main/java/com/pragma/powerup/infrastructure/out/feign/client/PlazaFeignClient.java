package com.pragma.powerup.infrastructure.out.feign.client;

import com.pragma.powerup.infrastructure.out.feign.dto.request.RestaurantEmployeeRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "plaza-ms",url = "${plaza.service.url}")
public interface PlazaFeignClient {
    @PostMapping("/employees")
    void assignEmployee(@RequestBody RestaurantEmployeeRequestDto restaurantEmployeeRequestDto);

    @GetMapping("/restaurants/ownership")
    boolean getOwnership(@RequestParam int id, @RequestParam int ownerId);
}