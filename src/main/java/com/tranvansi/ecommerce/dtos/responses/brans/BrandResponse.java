package com.tranvansi.ecommerce.dtos.responses.brans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponse {
    private String id;
    private String name;
    private String summary;
}
