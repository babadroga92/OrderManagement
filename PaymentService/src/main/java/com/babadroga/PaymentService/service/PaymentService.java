package com.babadroga.PaymentService.service;

import com.babadroga.PaymentService.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
