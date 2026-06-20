package com.prueba.client;

import com.prueba.dto.ProductoResponse;
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
public class ProductoClient {

    private final WebClient webClient;

    public ProductoClient(
            @Value("${services.producto.url:http://localhost:8086}")
            String productoUrl
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(productoUrl)
                .build();
    }

    public ProductoResponse obtenerProductoPorId(Long id) {

        log.info("Consultando producto {}", id);

        try {
            ProductoResponse producto = webClient.get()
                    .uri("/api/productos/{id}", id)
                    .retrieve()
                    .bodyToMono(ProductoResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (producto == null) {
                throw new NotFoundException(
                        "Producto no encontrado"
                );
            }

            return producto;

        } catch (WebClientResponseException ex) {

            log.error("Error HTTP consultando producto {}", id,
                    ex);

            switch (ex.getStatusCode().value()) {
                case 404 -> throw new NotFoundException(
                        "Producto no encontrado"
                );
                case 409 -> throw new BusinessException(
                        "Conflicto al consultar producto"
                );
                default -> throw new RuntimeException(
                        "Error al consultar servicio producto"
                );
            }
        }
    }
}