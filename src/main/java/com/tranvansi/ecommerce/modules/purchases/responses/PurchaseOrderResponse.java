package com.tranvansi.ecommerce.modules.purchases.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.common.enums.PurchaseOrderStatus;

import com.tranvansi.ecommerce.modules.products.responses.VariantResponse;
import com.tranvansi.ecommerce.modules.suppliers.responses.SupplierResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderResponse {

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
        private VariantResponse variant;
    }
}
