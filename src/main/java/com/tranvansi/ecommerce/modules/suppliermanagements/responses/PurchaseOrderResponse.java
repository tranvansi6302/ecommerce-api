package com.tranvansi.ecommerce.modules.suppliermanagements.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.PurchaseOrderStatus;
import com.tranvansi.ecommerce.modules.productmanagements.responses.VariantResponse;

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

    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    private String note;

    private String purchaseOrderDate;

    private PurchaseOrderStatus status;

    private SupplierResponse supplier;

    @JsonProperty("purchase_details")
    private List<PurchaseDetailResponse> purchaseDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PurchaseDetailResponse {
        private Integer quantity;

        @JsonProperty("purchase_price")
        private Double purchasePrice;

        @JsonProperty("quantity_received")
        private Integer quantityReceived;

        private VariantResponse variant;
    }
}
