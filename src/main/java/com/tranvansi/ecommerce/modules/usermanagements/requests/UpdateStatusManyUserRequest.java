package com.tranvansi.ecommerce.modules.usermanagements.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusManyUserRequest {
    @JsonProperty("user_ids")
    @NotNull(message = "INVALID_USER_IDS_REQUIRED")
    private Integer[] userIds;
}
