package com.tranvansi.ecommerce.dtos.responses.suppliers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponse {
    private Integer id;

    private String name;

    private String taxCode;

    private String email;

    private String phoneNumber;

    private String address;
}
