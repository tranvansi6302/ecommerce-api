package com.tranvansi.ecommerce.services.sizes;

import com.tranvansi.ecommerce.dtos.requests.sizes.CreateSizeRequest;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ISizeService {
    @PreAuthorize("hasRole('ADMIN')")
    SizeResponse createSize(CreateSizeRequest request);

    Page<SizeResponse> getAllSizes(PageRequest pageRequest);

    SizeResponse getSizeById(String id);
}
