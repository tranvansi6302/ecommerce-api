package com.tranvansi.ecommerce.modules.salesmanagement.services;

import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.salesmanagement.entities.Sale;
import com.tranvansi.ecommerce.modules.salesmanagement.repositories.SaleRepository;
import com.tranvansi.ecommerce.modules.salesmanagement.services.interfaces.ISaleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService {
    private final SaleRepository saleRepository;

    @Override
    public void saveSale(Sale sale) {
        saleRepository.save(sale);
    }

    @Override
    public Integer findTotalSoldByProductId(Integer productId) {
        return saleRepository.findTotalSoldByProductId(productId);
    }
}
