package com.tranvansi.ecommerce.modules.usermanagements.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.usermanagements.entities.User;

@Repository
public interface UserRepository
        extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

}
