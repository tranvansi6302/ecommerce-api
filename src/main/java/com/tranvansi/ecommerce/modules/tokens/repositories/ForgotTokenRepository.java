package com.tranvansi.ecommerce.modules.tokens.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.tokens.entities.ForgotToken;

@Repository
public interface ForgotTokenRepository extends JpaRepository<ForgotToken, Integer> {
    Optional<ForgotToken> findByToken(String token);

    ForgotToken findByEmail(String email);
}
