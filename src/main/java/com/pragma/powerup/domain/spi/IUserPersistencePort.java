package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.UserModel;

import java.util.List;

public interface IUserPersistencePort {
    void saveUser(UserModel user);
    List<UserModel> getAllUsers();
    UserModel getUserById(Integer id);
    UserModel getUserByEmail(String email);
}
