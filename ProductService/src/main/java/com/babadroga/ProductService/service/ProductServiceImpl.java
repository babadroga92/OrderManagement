package com.babadroga.ProductService.service;

import com.babadroga.ProductService.dao.ProductDao;
import com.babadroga.ProductService.entity.Product;
import com.babadroga.ProductService.exception.ProductServiceCustomException;
import com.babadroga.ProductService.model.ProductRequest;
import com.babadroga.ProductService.model.ProductResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product..");
        Product product
                = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        productDao.save(product);
        log.info("Product created.");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productId: {}", productId);

        Product product =
                productDao.findById(productId)
                        .orElseThrow(() -> new ProductServiceCustomException("Product with given id not found. ", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse
                = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for Id: {}", productId, quantity);

        Product product = productDao.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given Id not found", "PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient quantity", "INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productDao.save(product);

        log.info("Product Quantity updated successfully");
    }

    @Override
    public void updateQuantity(String name, long quantity) {
        log.info("Update Quantity {} for name: {}", quantity, name);
        Product product = productDao.findByName(name);
        product.setQuantity(product.getQuantity() + quantity);
        productDao.save(product);
        log.info("Product Quantity updated successfully");
    }
}
