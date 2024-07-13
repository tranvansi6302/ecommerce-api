package com.tranvansi.ecommerce.components.services.payments;

import com.tranvansi.ecommerce.components.enums.OrderStatus;
import com.tranvansi.ecommerce.components.enums.PaidStatus;
import com.tranvansi.ecommerce.components.enums.PaymentMethodType;
import com.tranvansi.ecommerce.components.requests.CreatePaymentMomoRequest;
import com.tranvansi.ecommerce.components.responses.MomoResponse;
import com.tranvansi.ecommerce.modules.ordermanagements.entities.Order;
import com.tranvansi.ecommerce.modules.ordermanagements.repositories.OrderRepository;
import com.tranvansi.ecommerce.modules.ordermanagements.services.interfaces.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

@Service
@Slf4j
@RequiredArgsConstructor
public class MomoService {
    private final IOrderService orderService;
    @Value("${payment.momo.partner-code}")
    private String PARTNER_CODE;
    @Value("${payment.momo.access-key}")
    private String ACCESS_KEY;

    @Value("${payment.momo.secret-key}")
    private String SECRET_KEY;

    @Value("${payment.momo.redirect-url}")
    private String REDIRECT_URL;

    @Value("${payment.momo.ipn-url}")
    private String IPN_URL;

    @Value("${payment.momo.request-type}")
    private String REQUEST_TYPE;

    public MomoResponse createPaymentMomo(CreatePaymentMomoRequest request) throws NoSuchAlgorithmException, InvalidKeyException {
        log.info("IPN_URL: {}", IPN_URL);
        String requestId = PARTNER_CODE + System.currentTimeMillis() + "id";
        String orderId = request.getOrderId() + ":" + System.currentTimeMillis();
        String orderInfo = "Thanh toán qua ví MoMo";
        String extraData = String.format("orderId=%s", request.getOrderId());

        String rawSignature = "accessKey=" + ACCESS_KEY + "&amount=" + request.getAmount().toString() + "&extraData=" + extraData + "&ipnUrl=" + IPN_URL + "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + PARTNER_CODE + "&redirectUrl=" + REDIRECT_URL + "&requestId=" + requestId + "&requestType=" + REQUEST_TYPE;

        String signature = generateSignature(rawSignature, SECRET_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("partnerCode", PARTNER_CODE);
        requestBody.put("accessKey", ACCESS_KEY);
        requestBody.put("requestId", requestId);
        requestBody.put("amount", request.getAmount().toString());
        requestBody.put("orderId", orderId);
        requestBody.put("orderInfo", orderInfo);
        requestBody.put("redirectUrl", REDIRECT_URL);
        requestBody.put("ipnUrl", IPN_URL);
        requestBody.put("extraData", extraData);
        requestBody.put("requestType", REQUEST_TYPE);
        requestBody.put("signature", signature);
        requestBody.put("lang", "en");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        var responseEntity = restTemplate.postForEntity("https://test-payment.momo.vn/v2/gateway/api/create", entity, Map.class);
        var payUrl = (String) responseEntity.getBody().get("payUrl");
        return MomoResponse
                .builder()
                .qrCode(payUrl)
                .build();
    }

    public void callback(Map<String, Object> payload) {
        // Get extraData
        var extraData = (String) payload.get("extraData");
        String orderId = extraData.split("=")[1];
        Integer resultCode = (Integer) payload.get("resultCode");
        Order order = orderService.findByOrderCode(orderId);

        if (order != null) {
            order.setPaymentMethod(PaymentMethodType.MOMO);
            if (resultCode == 0) {
                order.setStatus(OrderStatus.PAID);
                order.setOnlinePaymentStatus(PaidStatus.PAID);
                order.setPaidDate(LocalDateTime.now());

            } else {
                order.setStatus(OrderStatus.UNPAID);
                order.setOnlinePaymentStatus(PaidStatus.UNPAID);
            }
            orderService.saveOrder(order);
        }
    }

    public String generateSignature(String rawSignature, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);

        return Hex.encodeHexString(sha256_HMAC.doFinal(rawSignature.getBytes(StandardCharsets.UTF_8)));
    }
}
