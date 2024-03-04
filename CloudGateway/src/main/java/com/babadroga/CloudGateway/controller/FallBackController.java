package com.babadroga.CloudGateway.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {
    @RequestMapping("/orderServiceFallBack")

    public String orderServiceFallback(){
        return "Order Service is currently down! ";
    }

    @RequestMapping("/paymentServiceFallBack")
    public String paymentServiceFallback(){
        return "Payment Service is currently down! ";
    }

    @RequestMapping("/productServiceFallBack")
    public String productServiceFallback(){
        return "Product Service is currently down! ";
    }



}
