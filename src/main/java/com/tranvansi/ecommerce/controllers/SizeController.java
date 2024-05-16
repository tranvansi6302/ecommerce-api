package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.sizes.CreateSizeRequest;
import com.tranvansi.ecommerce.dtos.requests.sizes.UpdateSizeRequest;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.common.BuildResponse;
import com.tranvansi.ecommerce.dtos.responses.common.PagedResponse;
import com.tranvansi.ecommerce.dtos.responses.sizes.SizeResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.sizes.ISizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final ISizeService sizeService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<SizeResponse>>> getAllSizes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction
    ) {
        Sort sort = sort_direction.equalsIgnoreCase("asc") ?
                Sort.by("createdAt").ascending() :
                Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<SizeResponse> categoryResponses = sizeService.getAllSizes(pageRequest);
        PagedResponse<List<SizeResponse>> response = BuildResponse
                .buildPagedResponse(categoryResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> getSizeById(@PathVariable String id) {
        SizeResponse sizeResponse = sizeService.getSizeById(id);
        ApiResponse<SizeResponse> response = ApiResponse.<SizeResponse>builder()
                .result(sizeResponse)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("")
    public ResponseEntity<ApiResponse<SizeResponse>> createSize(@RequestBody @Valid CreateSizeRequest request) {
        SizeResponse sizeResponse = sizeService.createSize(request);
        ApiResponse<SizeResponse> response = ApiResponse.<SizeResponse>builder()
                .result(sizeResponse)
                .message(Message.CREATE_SIZE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> updateSize(
            @PathVariable String id, @RequestBody @Valid UpdateSizeRequest request) {
        SizeResponse sizeResponse = sizeService.updateSize(id, request);
        ApiResponse<SizeResponse> response = ApiResponse.<SizeResponse>builder()
                .result(sizeResponse)
                .message(Message.UPDATE_SIZE_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
