package com.tranvansi.ecommerce.services.price_lists;

import com.tranvansi.ecommerce.dtos.requests.price_lists.CreatePriceListRequest;
import com.tranvansi.ecommerce.dtos.responses.price_lists.CreatePriceListResponse;

public interface IPriceListService {
    CreatePriceListResponse createPriceList(CreatePriceListRequest request);
}
