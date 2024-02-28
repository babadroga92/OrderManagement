package com.babadroga.PaymentService.service;

import com.babadroga.PaymentService.model.PaymentRequest;
import com.babadroga.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
