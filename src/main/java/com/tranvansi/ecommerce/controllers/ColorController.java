package com.tranvansi.ecommerce.controllers;

import com.tranvansi.ecommerce.dtos.requests.colors.CreateColorRequest;
import com.tranvansi.ecommerce.dtos.requests.colors.UpdateColorRequest;
import com.tranvansi.ecommerce.dtos.responses.colors.ColorResponse;
import com.tranvansi.ecommerce.dtos.responses.common.ApiResponse;
import com.tranvansi.ecommerce.dtos.responses.common.BuildResponse;
import com.tranvansi.ecommerce.dtos.responses.common.PagedResponse;
import com.tranvansi.ecommerce.enums.Message;
import com.tranvansi.ecommerce.services.colors.IColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/colors")
@RequiredArgsConstructor
public class ColorController {
    private final IColorService colorService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<List<ColorResponse>>> getAllColors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(defaultValue = "desc") String sort_direction
    ) {
        Sort sort = sort_direction.equalsIgnoreCase("asc") ?
                Sort.by("createdAt").ascending() :
                Sort.by("createdAt").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);
        Page<ColorResponse> colorResponses = colorService.getAllColors(pageRequest);
        PagedResponse<List<ColorResponse>> response = BuildResponse.buildPagedResponse(colorResponses, pageRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> getColorById(@PathVariable String id) {
        ColorResponse colorResponse = colorService.getColorById(id);
        ApiResponse<ColorResponse> response = ApiResponse.<ColorResponse>builder()
                .result(colorResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<ColorResponse>> createColor(@RequestBody @Valid CreateColorRequest request) {
        ColorResponse colorResponse = colorService.createColor(request);
        ApiResponse<ColorResponse> response = ApiResponse.<ColorResponse>builder()
                .result(colorResponse)
                .message(Message.CREATE_COLOR_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> updateColor(
            @PathVariable String id, @RequestBody @Valid UpdateColorRequest request) {
        ColorResponse colorResponse = colorService.updateColor(id, request);
        ApiResponse<ColorResponse> response = ApiResponse.<ColorResponse>builder()
                .result(colorResponse)
                .message(Message.UPDATE_COLOR_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteColor(@PathVariable String id) {
        colorService.deleteColor(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(Message.DELETE_COLOR_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }
}
