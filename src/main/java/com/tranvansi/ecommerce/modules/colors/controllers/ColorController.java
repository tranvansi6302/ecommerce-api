package com.tranvansi.ecommerce.modules.colors.controllers;

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
import com.tranvansi.ecommerce.modules.colors.requests.CreateColorRequest;
import com.tranvansi.ecommerce.modules.colors.requests.UpdateColorRequest;
import com.tranvansi.ecommerce.modules.colors.responses.ColorResponse;
import com.tranvansi.ecommerce.modules.colors.services.IColorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/colors")
@RequiredArgsConstructor
public class ColorController {
    private final IColorService colorService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<ColorResponse>>> getAllColors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction) {
        Sort sort =
                sort_direction.equalsIgnoreCase("asc")
                        ? Sort.by("createdAt").ascending()
                        : Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<ColorResponse> colorResponses = colorService.getAllColors(pageRequest);
        PagedResponse<List<ColorResponse>> response =
                BuildResponse.buildPagedResponse(colorResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> getColorById(@PathVariable Integer id) {
        ColorResponse colorResponse = colorService.getColorById(id);
        ApiResponse<ColorResponse> response =
                ApiResponse.<ColorResponse>builder().result(colorResponse).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<ColorResponse>> createColor(
            @RequestBody @Valid CreateColorRequest request) {
        ColorResponse colorResponse = colorService.createColor(request);
        ApiResponse<ColorResponse> response =
                ApiResponse.<ColorResponse>builder()
                        .result(colorResponse)
                        .message(Message.CREATE_COLOR_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> updateColor(
            @PathVariable Integer id, @RequestBody @Valid UpdateColorRequest request) {
        ColorResponse colorResponse = colorService.updateColor(id, request);
        ApiResponse<ColorResponse> response =
                ApiResponse.<ColorResponse>builder()
                        .result(colorResponse)
                        .message(Message.UPDATE_COLOR_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteColor(@PathVariable Integer id) {
        colorService.deleteColor(id);
        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .message(Message.DELETE_COLOR_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }
}