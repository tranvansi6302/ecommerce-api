package com.tranvansi.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tranvansi.ecommerce.entities.PriceList;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Integer> {}
