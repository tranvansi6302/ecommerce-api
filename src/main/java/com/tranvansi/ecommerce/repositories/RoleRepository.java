package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}