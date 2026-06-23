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
@Schema(description = "Información de un entrenador registrado en el gimnasio")
public class EntrenadorResponse {

    @Schema(
            description = "Identificador único del entrenador",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre completo del entrenador",
            example = "Pedro González"
    )
    private String nombre;

    @Schema(
            description = "Especialidad o área de entrenamiento del entrenador",
            example = "Calistenia"
    )
    private String especialidad;

    @Schema(
            description = "Número de teléfono del entrenador",
            example = "+56998765432"
    )
    private String telefono;

    @Schema(
            description = "Indica si el entrenador se encuentra activo en el gimnasio",
            example = "true"
    )
    private Boolean activo;
}
