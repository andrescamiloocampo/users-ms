package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getByEmail(String email);
    boolean existsByEmail(String email);
    List<UserEntity> findByRoles_Name(String roleName);
}
