package com.tranvansi.ecommerce.modules.usermanagements.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.usermanagements.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Page<Address> findByUserId(Integer userId, Pageable pageable);

    boolean existsByUserIdAndIsDefault(Integer userId, Integer isDefault);

    Optional<Address> findByIsDefaultAndUserId(Integer isDefault, Integer userId);

    boolean existsByUserIdAndId(Integer userId, Integer id);
}
