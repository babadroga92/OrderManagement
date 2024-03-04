package com.babadroga.OrderService.service;

import com.babadroga.OrderService.entity.Order;
import com.babadroga.OrderService.model.OrderRequest;
import com.babadroga.OrderService.model.OrderResponse;

import java.util.List;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
    List<Order> listOfAllOrders();
}
