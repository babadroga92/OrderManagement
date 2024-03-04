package com.babadroga.OrderService.external.client;

import com.babadroga.OrderService.exception.OrderServiceCustomException;
import com.babadroga.OrderService.external.client.request.PaymentRequest;
import com.babadroga.OrderService.external.client.response.PaymentResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId);

    default void fallback(FeignException e){
        throw new OrderServiceCustomException("Payment Service is not available", "UNAVAILABLE", 500);
    }

    // TODO: Fix fallback method as it currently doesn't work
}
