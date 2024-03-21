package com.babadroga.OrderService.config;

import com.babadroga.OrderService.external.client.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    //This class is a Feign configuration to use CustomErrorDecoder to handle error responses from the server
    @Bean
    ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }
}
