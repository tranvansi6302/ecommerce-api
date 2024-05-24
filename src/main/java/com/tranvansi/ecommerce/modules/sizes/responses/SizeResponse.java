package com.tranvansi.ecommerce.modules.sizes.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeResponse {
    private Integer id;
    private String name;
    private String summary;
}
