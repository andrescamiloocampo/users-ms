package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.RoleModel;

public interface IRoleServicePort {
    RoleModel saveRole(RoleModel role);
}
