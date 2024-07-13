package com.tranvansi.ecommerce.modules.ordermanagements.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.modules.ordermanagements.mappers.VoucherMapper;
import com.tranvansi.ecommerce.modules.ordermanagements.repositories.VoucherRepository;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.VoucherResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.IVoucherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoucherService implements IVoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;

    @Override
    public Page<VoucherResponse> getAllVouchers(PageRequest pageRequest) {
        return voucherRepository.findAll(pageRequest).map(voucherMapper::toVoucherResponse);
    }
}
