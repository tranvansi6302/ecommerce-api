package com.tranvansi.ecommerce.modules.usermanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.usermanagements.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
