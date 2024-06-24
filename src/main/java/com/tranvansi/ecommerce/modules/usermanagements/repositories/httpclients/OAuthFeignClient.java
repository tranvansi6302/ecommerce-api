package com.tranvansi.ecommerce.modules.usermanagements.repositories.httpclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.tranvansi.ecommerce.modules.usermanagements.requests.ExchangeTokenRequest;
import com.tranvansi.ecommerce.modules.usermanagements.responses.ExchangeTokenResponse;

import feign.QueryMap;

@FeignClient(name = "oauth-service", url = "https://oauth2.googleapis.com")
public interface OAuthFeignClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest request);
}
