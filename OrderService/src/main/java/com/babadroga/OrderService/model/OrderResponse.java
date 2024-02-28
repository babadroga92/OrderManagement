package com.babadroga.OrderService.model;

import com.babadroga.OrderService.external.client.productDetails.ProductDetails;
import com.babadroga.OrderService.external.client.response.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private ProductDetails productDetails;
    private PaymentResponse paymentResponse;
}
