package com.babadroga.OrderService.service;

import com.babadroga.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
