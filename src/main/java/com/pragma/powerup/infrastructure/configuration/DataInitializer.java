package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataInitializer {

    private final IRoleRepository roleRepository;

    public DataInitializer(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        List<String> defaultRoles = List.of("OWNER", "ADMIN", "CUSTOMER", "EMPLOYEE");

        for (String roleName : defaultRoles) {
            if (!roleRepository.existsByName(roleName)) {
                RoleEntity role = new RoleEntity();
                role.setName(roleName);
                role.setDescription("Grant " + roleName.toLowerCase() + " access");
                roleRepository.save(role);
            }
        }
    }
}
