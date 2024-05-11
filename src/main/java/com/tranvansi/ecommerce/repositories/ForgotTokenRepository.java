package com.tranvansi.ecommerce.repositories;

import com.tranvansi.ecommerce.entities.ForgotToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotTokenRepository extends JpaRepository<ForgotToken, String> {
    Optional<ForgotToken> findByToken(String token);
    ForgotToken findByEmail(String email);
}