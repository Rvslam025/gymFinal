package com.prueba.client;

import com.prueba.dto.EntrenadorResponse;
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
public class EntrenadorClient {

    private final WebClient webClient;

    public EntrenadorClient(
            @Value("${services.entrenador.url:http://localhost:8086}")
            String entrenadorUrl
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(entrenadorUrl)
                .build();
    }

    public EntrenadorResponse obtenerEntrenadorPorId(Long id) {

        log.info("Consultando entrenador {}", id);

        try {
            EntrenadorResponse entrenador = webClient.get()
                    .uri("/api/entrenadores/{id}", id)
                    .retrieve()
                    .bodyToMono(EntrenadorResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (entrenador == null) {
                throw new NotFoundException(
                        "Entrenador no encontrado"
                );
            }

            return entrenador;

        } catch (WebClientResponseException ex) {

            log.error("Error HTTP entrenador {}", id, ex);

            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NotFoundException(
                        "Entrenador no encontrado"
                );
                case 409 -> throw new BusinessException(
                        "Conflicto al consultar entrenador"
                );
                default -> throw new RuntimeException(
                        "Error al consultar servicio entrenador"
                );
            }
        }
    }
}
