package com.babadroga.OrderService.service;

import com.babadroga.OrderService.model.OrderRequest;
import com.babadroga.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
