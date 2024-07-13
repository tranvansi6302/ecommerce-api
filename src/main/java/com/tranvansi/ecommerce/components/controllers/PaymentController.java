package com.tranvansi.ecommerce.components.controllers;

import com.tranvansi.ecommerce.components.enums.Message;
import com.tranvansi.ecommerce.components.requests.CreatePaymentMomoRequest;
import com.tranvansi.ecommerce.components.responses.ApiResponse;
import com.tranvansi.ecommerce.components.responses.MomoResponse;
import com.tranvansi.ecommerce.components.services.payments.MomoService;
import com.tranvansi.ecommerce.modules.productmanagements.responses.BrandResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

@RestController
@RequestMapping("${api.prefix}/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

   private final MomoService momoService;

    @PostMapping("/momo")
    public ResponseEntity<ApiResponse<MomoResponse>> createPaymentMomo(@RequestBody @Valid CreatePaymentMomoRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
        MomoResponse momoResponse = momoService.createPaymentMomo(request);
        ApiResponse<MomoResponse> response =
                ApiResponse.<MomoResponse>builder()
                        .result(momoResponse)
                        .message(Message.MOMO_QR_CODE_SUCCESS.getMessage())
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/callback")
    public void callBackMomo(@RequestBody Map<String, Object> payload) {

        momoService.callback(payload);
    }

    public String generateSignature(String rawSignature, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);

        return Hex.encodeHexString(sha256_HMAC.doFinal(rawSignature.getBytes(StandardCharsets.UTF_8)));
    }
}
