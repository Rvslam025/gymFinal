package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar una membresía de un cliente")
public class MembresiaRequest {

    @Schema(
            description = "Identificador único del cliente al que se le asignará la membresía",
            example = "1"
    )
    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    @Schema(
            description = "Identificador único del tipo de membresía que se asignará al cliente",
            example = "2"
    )
    @NotNull(message = "El tipo de membresía es obligatorio")
    private Long tipoMembresiaId;
}
