package com.prueba.Client;

import com.prueba.exception.BusinessException;
import com.prueba.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class MembresiaClient {

    @Autowired
    private WebClient webClient;

    public Boolean validarMembresia(Long userId) {

        log.info("Consultando membresía activa del usuario {}", userId);

        try {
            return webClient.get()
                    .uri("/api/membresias/activa/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        } catch (WebClientResponseException ex) {

            log.error("Error al consultar membresía usuario {}", userId, ex);

            switch (ex.getStatusCode().value()) {

                case 401 -> throw new BusinessException(
                        "No autorizado para acceder al servicio de membresías"
                );

                case 403 -> throw new BusinessException(
                        "Acceso prohibido al servicio de membresías"
                );

                case 404 -> throw new NotFoundException(
                        "Membresía no encontrada para usuario " + userId
                );

                default -> throw new RuntimeException(
                        "Error interno al consultar membresía"
                );
            }
        }
    }
}
