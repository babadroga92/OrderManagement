package com.babadroga.OrderService.external.client.intercept;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestClientConfig {
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate =
                new RestTemplate();
        restTemplate.setInterceptors(
                Arrays.asList(
                        new RestTemplateInterceptor(
                                clientManager(clientRegistrationRepository,
                                        oAuth2AuthorizedClientRepository)
                        )
                )
        );
        return restTemplate;
    }
    @Bean
    public RestTemplateInterceptor restTemplateInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        return new RestTemplateInterceptor(oAuth2AuthorizedClientManager);
    }
    @Bean
    public OAuth2AuthorizedClientManager clientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository
    ){
        OAuth2AuthorizedClientProvider oAuth2AuthorizedClientProvider
                = OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();
        DefaultOAuth2AuthorizedClientManager oAuth2AuthorizedClientManager
                = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientRepository);
        oAuth2AuthorizedClientManager.setAuthorizedClientProvider(
                oAuth2AuthorizedClientProvider
        );
        return oAuth2AuthorizedClientManager;
    }

}
