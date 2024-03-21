package com.babadroga.OrderService.external.client.decoder;

import com.babadroga.OrderService.exception.OrderServiceCustomException;
import com.babadroga.OrderService.external.client.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) { //this method is called by Feign when it encounters an error response from the server
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());
        try {
            //converting the error response from the server into a Java object
            ErrorResponse errorResponse =
                    objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new OrderServiceCustomException(errorResponse.getErrorMessage(), errorResponse.getErrorCode(), response.status());
        } catch (IOException e) {
            throw new OrderServiceCustomException("Internal server error", "INTERNAL_SERVER_ERROR", 500);
        }
    }
}
