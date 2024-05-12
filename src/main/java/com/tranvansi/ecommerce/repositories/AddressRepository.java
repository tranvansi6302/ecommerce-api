package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByIdAndUserId(String id, String userId);
}