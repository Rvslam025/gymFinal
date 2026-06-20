package com.prueba.client;

import com.prueba.dto.ClienteResponse;
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
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(@Value("${services.cliente.url:http://localhost:8083}") String clienteUrl) {
        this.webClient = WebClient.builder().baseUrl(clienteUrl).build();
    }

    public ClienteResponse obtenerClientePorId(Long id) {
        log.info("Consultando cliente remoto con id: {}", id);
        try {
            ClienteResponse cliente = webClient.get()
                    .uri("/api/clientes/{id}", id)
                    .retrieve()
                    .bodyToMono(ClienteResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (cliente == null) {
                throw new NotFoundException("Cliente con id " + id + " no encontrado");
            }
            return cliente;

        } catch (WebClientResponseException ex) {
            log.error("Error HTTP al consultar cliente id {}: status={}", id, ex.getStatusCode());
            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NotFoundException("Cliente con id " + id + " no encontrado");
                case 409 -> throw new BusinessException("Conflicto al consultar cliente");
                default -> throw new RuntimeException("Error al consultar el servicio de clientes");
            }
        }
    }
}
