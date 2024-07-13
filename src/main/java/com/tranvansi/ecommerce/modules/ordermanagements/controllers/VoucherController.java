package com.tranvansi.ecommerce.modules.ordermanagements.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tranvansi.ecommerce.components.responses.BuildResponse;
import com.tranvansi.ecommerce.components.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.VoucherResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.IVoucherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final IVoucherService voucherService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<VoucherResponse>>> getAllVouchers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<VoucherResponse> voucherResponses = voucherService.getAllVouchers(pageRequest);
        PagedResponse<List<VoucherResponse>> response =
                BuildResponse.buildPagedResponse(voucherResponses, pageRequest);
        return ResponseEntity.ok(response);
    }
}
