package com.prueba.client;

import com.prueba.dto.VentaResponse;
import com.prueba.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Slf4j
@Component
public class VentaClient {

    private final WebClient webClient;

    public VentaClient(@Value("${services.venta.url:http://localhost:8089}") String ventaUrl) {
        this.webClient = WebClient.builder().baseUrl(ventaUrl).build();
    }

    public VentaResponse obtenerVentaPorId(Long id) {
        log.info("Consultando venta remota con id: {}", id);
        try {
            VentaResponse venta = webClient.get()
                    .uri("/api/ventas/{id}", id)
                    .retrieve()
                    .bodyToMono(VentaResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (venta == null) throw new NotFoundException("Venta con id " + id + " no encontrada");
            return venta;

        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().value() == 404)
                throw new NotFoundException("Venta con id " + id + " no encontrada");
            throw new RuntimeException("Error al consultar el servicio de ventas");
        }
    }
}
