package com.tranvansi.ecommerce.dtos.responses.purchases;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.tranvansi.ecommerce.dtos.responses.suppliers.SupplierResponse;
import com.tranvansi.ecommerce.dtos.responses.users.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderResponse {

    private Integer id;

    private String purchaseOrderCode;

    private LocalDateTime purchaseOrderDate;

    private Integer status;

    private Integer paymentStatus;

    private String note;

    private UserResponse user;

    private SupplierResponse supplier;
}
