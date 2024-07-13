package com.tranvansi.ecommerce.modules.ordermanagements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {}
