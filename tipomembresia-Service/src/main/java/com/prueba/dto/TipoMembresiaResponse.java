package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Información de un tipo de membresía registrado en el sistema")
public class TipoMembresiaResponse {

    @Schema(
            description = "Identificador único del tipo de membresía",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del tipo de membresía",
            example = "Mensual"
    )
    private String nombre;

    @Schema(
            description = "Descripción de la membresía y sus beneficios",
            example = "Acceso ilimitado al gimnasio durante 30 días"
    )
    private String descripcion;

    @Schema(
            description = "Precio del tipo de membresía en pesos chilenos",
            example = "29990.0"
    )
    private Double precio;

    @Schema(
            description = "Duración de la membresía expresada en días",
            example = "30"
    )
    private Integer duracionDias;
}
