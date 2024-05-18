package com.tranvansi.ecommerce.services.sizes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tranvansi.ecommerce.dtos.requests.sizes.CreateSizeRequest;
import com.tranvansi.ecommerce.dtos.requests.sizes.UpdateSizeRequest;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;

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
