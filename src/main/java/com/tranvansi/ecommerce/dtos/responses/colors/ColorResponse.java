package com.tranvansi.ecommerce.dtos.responses.colors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorResponse {
    private String id;
    private String name;
    private String hex;
    private String summary;
}
