package com.tranvansi.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByIdAndUserId(Integer id, Integer userId);
}
