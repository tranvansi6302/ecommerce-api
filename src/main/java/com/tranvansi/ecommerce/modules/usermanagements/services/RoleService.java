package com.tranvansi.ecommerce.modules.usermanagements.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.components.enums.RoleName;
import com.tranvansi.ecommerce.modules.usermanagements.entities.Role;
import com.tranvansi.ecommerce.modules.usermanagements.repositories.RoleRepository;
import com.tranvansi.ecommerce.modules.usermanagements.services.interfaces.IRoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAllRoleById(Set<String> ids) {
        return roleRepository.findAllById(Collections.singleton(RoleName.USER.name()));
    }
}
