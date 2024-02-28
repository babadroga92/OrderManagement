package com.babadroga.OrderService.external.client.productDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {

    private String productName;
    private long productId;
    private long quantity;
    private long price;
}
