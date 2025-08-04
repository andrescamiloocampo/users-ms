package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {
    private String name;
    private String lastname;
    private int identityDocument;
    private String phone;
    private LocalDate birthdate;
    private String email;
    private String password;
}
