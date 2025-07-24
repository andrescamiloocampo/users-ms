package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RoleModel;

import java.util.List;

public interface IRolePersistencePort {
    RoleModel createRole(RoleModel roleModel);
    List<RoleModel> getRoles();
    RoleModel findByName(String name);
}
