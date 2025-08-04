package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDto {
    private int id;
    private String name;
    private String lastname;
    private String phone;
    private LocalDate birthdate;
    private RoleResponseDto role;
}
