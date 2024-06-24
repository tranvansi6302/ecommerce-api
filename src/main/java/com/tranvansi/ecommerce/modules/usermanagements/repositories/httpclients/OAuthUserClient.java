package com.tranvansi.ecommerce.modules.usermanagements.repositories.httpclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tranvansi.ecommerce.modules.usermanagements.responses.OAuthUserResponse;

@FeignClient(name = "oauth-user", url = "https://www.googleapis.com")
public interface OAuthUserClient {
    @GetMapping(value = "/oauth2/v1/userinfo")
    OAuthUserResponse getOAuthUser(
            @RequestParam("alt") String alt, @RequestParam("access_token") String accessToken);
}
