package com.prueba.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(
            @Value("${services.membresia.url:http://localhost:8085}") String membresiaUrl
    ) {
        return WebClient.builder()
                .baseUrl(membresiaUrl)
                .build();
    }
}
