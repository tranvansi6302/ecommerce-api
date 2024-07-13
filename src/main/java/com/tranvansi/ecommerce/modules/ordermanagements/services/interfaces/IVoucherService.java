package com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.ordermanagements.responses.VoucherResponse;

public interface IVoucherService {
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    Page<VoucherResponse> getAllVouchers(PageRequest pageRequest);
}
