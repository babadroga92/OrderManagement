package com.babadroga.OrderService.external.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    //This is a copy of ErrorResponse class from payment.model and product.model. It is used in CustomErrorDecoder to convert error response from the server into a Java object

    private String errorMessage;
    private String errorCode;

}
