package com.tranvansi.ecommerce.modules.ordermanagements.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tranvansi.ecommerce.components.enums.DiscountType;
import com.tranvansi.ecommerce.components.enums.VoucherType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherResponse {
    private Integer id;

    private String code;

    private String description;

    @JsonProperty("voucher_type")
    private VoucherType voucherType;

    @JsonProperty("discount_type")
    private DiscountType discountType;

    private Double value;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

    @JsonProperty("min_order_value")
    private Double minOrderValue;
}
