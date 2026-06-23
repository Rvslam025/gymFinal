package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Información de una reserva registrada en el sistema")
public class ReservaResponse {

    @Schema(
            description = "Identificador único de la reserva",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Fecha en la que se realizó la reserva",
            example = "2026-06-23T10:30:00.000+00:00"
    )
    private Date fechaReserva;

    @Schema(
            description = "Estado actual de la reserva",
            example = "CONFIRMADA"
    )
    private String estado;
}
