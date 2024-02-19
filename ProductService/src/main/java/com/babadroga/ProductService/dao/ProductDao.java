package com.babadroga.ProductService.dao;
import com.babadroga.ProductService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    @Query("Select p from Product p where p.productName = :productName")
    Product findByName(String productName);
}
