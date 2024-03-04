package com.babadroga.OrderService.external.client;

import com.babadroga.OrderService.exception.OrderServiceCustomException;
import com.babadroga.OrderService.external.client.productDetails.ProductDetails;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {

    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity);

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable("id") long productId);

    default void fallback(FeignException e){
        throw new OrderServiceCustomException("Product Service is not available", "UNAVAILABLE", 500);
    }
}
