package com.tranvansi.ecommerce.modules.brands.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponse {
    private Integer id;
    private String name;
    private String slug;
    private String summary;
}
