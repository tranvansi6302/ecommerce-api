package com.tranvansi.ecommerce.dtos.responses.sizes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeResponse {
    private String id;
    private String name;
    private String summary;
}
