package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRoleServicePort;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.spi.IRolePersistencePort;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort){
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public RoleModel saveRole(RoleModel role) {
        return rolePersistencePort.createRole(role);
    }
}
