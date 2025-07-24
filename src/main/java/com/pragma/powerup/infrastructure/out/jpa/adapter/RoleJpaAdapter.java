package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public RoleModel createRole(RoleModel roleModel) {
        RoleEntity roleEntity = roleRepository.save(roleEntityMapper.toEntity(roleModel));
        return roleEntityMapper.toRoleModel(roleEntity);
    }

    @Override
    public List<RoleModel> getRoles() {
        return List.of();
    }

    @Override
    public RoleModel findByName(String name) {
        RoleEntity roleEntity = roleRepository.findByName(name);
        return roleEntityMapper.toRoleModel(roleEntity);
    }
}
