package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public void saveUser(UserModel user) {
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userEntityMapper.toUserModelList(userRepository.findAll());
    }

    @Override
    public UserModel getUserById(Integer id) {
        return userEntityMapper.toUserModel(userRepository.findById(id).orElseThrow());
    }

}
