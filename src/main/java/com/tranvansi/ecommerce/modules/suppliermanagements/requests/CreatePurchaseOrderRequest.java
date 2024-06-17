package com.tranvansi.ecommerce.modules.suppliermanagements.requests;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePurchaseOrderRequest {
    @JsonProperty("supplier_id")
    @NotNull(message = "INVALID_PURCHASE_SUPPLIER_ID_REQUIRED")
    private Integer supplierId;

    @JsonProperty("purchase_order_code")
    @NotBlank(message = "INVALID_PURCHASE_ORDER_CODE_REQUIRED")
    private String purchaseOrderCode;

    private String note;

    @JsonProperty("purchase_details")
    @NotNull(message = "INVALID_PURCHASE_DETAILS_REQUIRED")
    private List<PurchaseDetailRequest> purchaseDetails;

    @Data
    public static class PurchaseDetailRequest {
        @NotNull(message = "INVALID_PURCHASE_DETAIL_QUANTITY_REQUIRED")
        private Integer quantity;

        @JsonProperty("variant_id")
        @NotNull(message = "INVALID_PURCHASE_DETAIL_VARIANT_ID_REQUIRED")
        private Integer variantId;

        private String note;

        @JsonProperty("purchase_price")
        @NotNull(message = "INVALID_PURCHASE_DETAIL_PURCHASE_PRICE_REQUIRED")
        private Double purchasePrice;
    }
}
