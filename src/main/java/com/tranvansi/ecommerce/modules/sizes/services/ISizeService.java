package com.tranvansi.ecommerce.modules.sizes.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.modules.sizes.requests.CreateSizeRequest;
import com.tranvansi.ecommerce.modules.sizes.requests.UpdateSizeRequest;
import com.tranvansi.ecommerce.modules.sizes.responses.SizeResponse;

public interface ISizeService {
    @PreAuthorize("hasRole('ADMIN')")
    SizeResponse createSize(CreateSizeRequest request);

    Page<SizeResponse> getAllSizes(PageRequest pageRequest);

    SizeResponse getSizeById(Integer id);

    @PreAuthorize("hasRole('ADMIN')")
    SizeResponse updateSize(Integer id, UpdateSizeRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteSize(Integer id);
}
