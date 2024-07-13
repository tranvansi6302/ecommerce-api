package com.tranvansi.ecommerce.modules.ordermanagements.mappers;

import org.mapstruct.Mapper;

import com.tranvansi.ecommerce.modules.ordermanagements.entities.Voucher;
import com.tranvansi.ecommerce.modules.ordermanagements.responses.VoucherResponse;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    VoucherResponse toVoucherResponse(Voucher voucher);
}
