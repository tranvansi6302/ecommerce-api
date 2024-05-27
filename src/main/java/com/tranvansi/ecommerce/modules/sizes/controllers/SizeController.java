package com.tranvansi.ecommerce.modules.sizes.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tranvansi.ecommerce.common.enums.Message;
import com.tranvansi.ecommerce.common.responses.ApiResponse;
import com.tranvansi.ecommerce.common.responses.BuildResponse;
import com.tranvansi.ecommerce.common.responses.PagedResponse;
import com.tranvansi.ecommerce.modules.sizes.requests.CreateSizeRequest;
import com.tranvansi.ecommerce.modules.sizes.requests.UpdateSizeRequest;
import com.tranvansi.ecommerce.modules.sizes.responses.SizeResponse;
import com.tranvansi.ecommerce.modules.sizes.services.ISizeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final ISizeService sizeService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<SizeResponse>>> getAllSizes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(name = "sort_order", defaultValue = "desc") String sortOrder) {
        Sort sort =
                sortOrder.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<SizeResponse> categoryResponses = sizeService.getAllSizes(pageRequest);
        PagedResponse<List<SizeResponse>> response =
                BuildResponse.buildPagedResponse(categoryResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> getSizeById(@PathVariable Integer id) {
        SizeResponse sizeResponse = sizeService.getSizeById(id);
        ApiResponse<SizeResponse> response =
                ApiResponse.<SizeResponse>builder().result(sizeResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<SizeResponse>> createSize(
            @RequestBody @Valid CreateSizeRequest request) {
        SizeResponse sizeResponse = sizeService.createSize(request);
        ApiResponse<SizeResponse> response =
                ApiResponse.<SizeResponse>builder()
                        .result(sizeResponse)
                        .message(Message.CREATE_SIZE_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> updateSize(
            @PathVariable Integer id, @RequestBody @Valid UpdateSizeRequest request) {
        SizeResponse sizeResponse = sizeService.updateSize(id, request);
        ApiResponse<SizeResponse> response =
                ApiResponse.<SizeResponse>builder()
                        .result(sizeResponse)
                        .message(Message.UPDATE_SIZE_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSize(@PathVariable Integer id) {
        sizeService.deleteSize(id);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.DELETE_SIZE_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}
