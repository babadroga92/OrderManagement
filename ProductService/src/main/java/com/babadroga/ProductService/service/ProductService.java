package com.babadroga.ProductService.service;

import com.babadroga.ProductService.model.ProductRequest;
import com.babadroga.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);

    void updateQuantity(String name, long quantity);
}
