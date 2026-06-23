package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Información de una membresía registrada para un cliente del gimnasio")
public class MembresiaResponse {

    @Schema(
            description = "Identificador único de la membresía",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Identificador único del cliente al que pertenece la membresía",
            example = "1"
    )
    private Long clienteId;

    @Schema(
            description = "Fecha de inicio de la membresía",
            example = "2026-06-23"
    )
    private LocalDate fechaInicio;

    @Schema(
            description = "Fecha de término de la membresía",
            example = "2026-07-23"
    )
    private LocalDate fechaFin;

    @Schema(
            description = "Indica si la membresía se encuentra activa",
            example = "true"
    )
    private Boolean activa;

    @Schema(
            description = "Nombre del tipo de membresía asociado",
            example = "Platino"
    )
    private String tipoMembresiaNombre;

    @Schema(
            description = "Precio de la membresía en pesos chilenos",
            example = "150000.0"
    )
    private Double precio;

    @Schema(
            description = "Duración de la membresía expresada en días",
            example = "30"
    )
    private Integer duracionDias;
}
