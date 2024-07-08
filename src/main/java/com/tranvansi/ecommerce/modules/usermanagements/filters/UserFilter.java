package com.tranvansi.ecommerce.modules.usermanagements.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilter {
    private String email;
    private Integer status;
    private Integer isDeleted;
}
