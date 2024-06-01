package com.tranvansi.ecommerce.modules.usermanagements.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.modules.usermanagements.entities.ForgotToken;

@Repository
public interface ForgotTokenRepository extends JpaRepository<ForgotToken, Integer> {
    Optional<ForgotToken> findByToken(String token);
}
