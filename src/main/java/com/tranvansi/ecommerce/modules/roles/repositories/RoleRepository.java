package com.tranvansi.ecommerce.modules.roles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.roles.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
