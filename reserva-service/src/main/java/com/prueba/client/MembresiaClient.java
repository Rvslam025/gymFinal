package com.prueba.client;

import com.prueba.exception.BusinessException;
import com.prueba.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Slf4j
@Component
public class MembresiaClient {

    private final WebClient webClient;

    public MembresiaClient(
            @Value("${services.membresia.url:http://localhost:8085}")
            String membresiaUrl
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(membresiaUrl)
                .build();
    }

    public Boolean validarMembresia(Long userId) {

        log.info("Validando membresía usuario {}", userId);

        try {
            Boolean activa = webClient.get()
                    .uri("/api/membresias/activa/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            return activa != null && activa;

        } catch (WebClientResponseException ex) {

            log.error("Error consultando membresía usuario {}", userId);

            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NotFoundException(
                        "Membresía no encontrada"
                );
                case 409 -> throw new BusinessException(
                        "Conflicto al consultar membresía"
                );
                default -> throw new RuntimeException(
                        "Error al consultar servicio membresía"
                );
            }
        }
    }
}
