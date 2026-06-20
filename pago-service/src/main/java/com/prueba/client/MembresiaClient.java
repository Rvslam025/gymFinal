package com.prueba.client;

import com.prueba.dto.MembresiaResponse;
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

    public MembresiaResponse obtenerMembresiaPorUserId(Long userId) {

        log.info("Consultando membresía del usuario {}", userId);

        try {
            MembresiaResponse membresia = webClient.get()
                    .uri("/api/membresias/{userId}", userId)
                    .retrieve()
                    .bodyToMono(MembresiaResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (membresia == null) {
                throw new NotFoundException(
                        "Membresía no encontrada para usuario " + userId
                );
            }

            return membresia;

        } catch (WebClientResponseException ex) {

            log.error("Error HTTP al consultar membresía {}", userId,
                    ex);

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
