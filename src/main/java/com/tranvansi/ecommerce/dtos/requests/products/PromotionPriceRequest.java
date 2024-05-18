package com.tranvansi.ecommerce.dtos.requests.products;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionPriceRequest {

    @NotNull(message = "INVALID_PROMOTION_PRICE_REQUIRED")
    private Double price;

    @NotNull(message = "INVALID_START_DATE_REQUIRED")
    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @NotNull(message = "INVALID_END_DATE_REQUIRED")
    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @AssertTrue(message = "INVALID_END_DATE")
    public boolean isValidEndDate() {
        return endDate.isAfter(startDate);
    }

}
