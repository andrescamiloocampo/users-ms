package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private int id;
    private String name;
    private String lastname;
    private int identityDocument;
    private String phone;
    private LocalDate birthdate;
    private String email;
    private String password;
    private RoleModel role;
}
