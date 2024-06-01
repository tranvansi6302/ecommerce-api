package com.tranvansi.ecommerce.modules.usermanagements.services.interfaces;

import java.util.List;
import java.util.Set;

import com.tranvansi.ecommerce.modules.usermanagements.entities.Role;

public interface IRoleService {
    List<Role> findAllRoleById(Set<String> ids);
}
