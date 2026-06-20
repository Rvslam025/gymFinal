package com.prueba.client;

import com.prueba.dto.ClaseResponse;
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
public class ClaseClient {

    private final WebClient webClient;

    public ClaseClient(
            @Value("${services.clase.url:http://localhost:8084}")
            String claseUrl
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(claseUrl)
                .build();
    }

    public ClaseResponse obtenerClase(Long id) {

        log.info("Consultando clase {}", id);

        try {
            ClaseResponse clase = webClient.get()
                    .uri("/api/clases/{id}", id)
                    .retrieve()
                    .bodyToMono(ClaseResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (clase == null) {
                throw new NotFoundException(
                        "Clase no encontrada"
                );
            }

            return clase;

        } catch (WebClientResponseException ex) {

            log.error("Error HTTP clase {}", id);

            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NotFoundException(
                        "Clase no encontrada"
                );
                case 409 -> throw new BusinessException(
                        "Conflicto al consultar clase"
                );
                default -> throw new RuntimeException(
                        "Error al consultar servicio clases"
                );
            }
        }
    }
}
