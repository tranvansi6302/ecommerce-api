package com.tranvansi.ecommerce.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tranvansi.ecommerce.dtos.requests.price_lists.PriceListRequest;
import com.tranvansi.ecommerce.entities.PriceList;

@Mapper(componentModel = "spring")
public interface PriceListMapper {
    @Mapping(target = "variant", ignore = true)
    PriceList createPriceList(PriceListRequest request);
}
