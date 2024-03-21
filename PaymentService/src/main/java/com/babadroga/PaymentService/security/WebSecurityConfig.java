package com.babadroga.PaymentService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/payment/**")
                        .hasAuthority("SCOPE_internal")
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(spec -> spec.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }
}
