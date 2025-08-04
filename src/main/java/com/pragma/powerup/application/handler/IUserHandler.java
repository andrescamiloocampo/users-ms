package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {
    void saveUser(UserRequestDto user);
    void saveOwner(UserRequestDto user);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Integer id);
}
