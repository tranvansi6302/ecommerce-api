package com.tranvansi.ecommerce.modules.suppliermanagements.requests;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.PurchaseOrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePurchaseOrderRequest {

    @NotNull(message = "INVALID_PURCHASE_ORDER_STATUS_REQUIRED")
    private PurchaseOrderStatus status;

    @JsonProperty("cancel_reason")
    private String cancelReason;

    @JsonProperty("purchase_details")
    @NotNull(message = "INVALID_PURCHASE_DETAILS_REQUIRED")
    private List<PurchaseDetailUpdateRequest> purchaseDetails;



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseDetailUpdateRequest {
        @JsonProperty("variant_id")
        @NotNull(message = "INVALID_VARIANT_ID_REQUIRED")
        private Integer variantId;

        private String note;

        @JsonProperty("quantity_received")
        @NotNull(message = "INVALID_QUANTITY_RECEIVED_REQUIRED")
        private Integer quantityReceived;
    }
}
