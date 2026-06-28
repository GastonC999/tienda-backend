package com.tutienda.backend.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @PostMapping("/create-preference")
    public Map<String, String> createPreference(@RequestBody List<Map<String, Object>> items) {
        try {
            MercadoPagoConfig.setAccessToken(accessToken);

            List<PreferenceItemRequest> mpItems = items.stream()
                    .map(item -> PreferenceItemRequest.builder()
                            .title((String) item.get("productName"))
                            .quantity((Integer) item.get("quantity"))
                            .unitPrice(new BigDecimal(item.get("price").toString()))
                            .build())
                    .toList();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:3000/checkout/success")
                    .failure("http://localhost:3000/checkout")
                    .pending("http://localhost:3000/checkout/success")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(mpItems)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = new PreferenceClient();

            Preference preference = client.create(preferenceRequest);

            System.out.println(preference.getId());

            return Map.of(
                    "initPoint",
                    preference.getSandboxInitPoint()
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}