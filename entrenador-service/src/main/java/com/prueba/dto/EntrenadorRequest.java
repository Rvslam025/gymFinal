package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar o modificar un entrenador del gimnasio")
public class EntrenadorRequest {

    @Schema(
            description = "Nombre completo del entrenador",
            example = "Pedro González"
    )
    @NotBlank(message = "El nombre no puede dejarlo vacio")
    private String nombre;

    @Schema(
            description = "Especialidad o área de entrenamiento del entrenador",
            example = "Calistenia"
    )
    @NotBlank(message = "La especialidad no puede estar vacia")
    private String especialidad;

    @Schema(
            description = "Número de teléfono del entrenador",
            example = "+56998765432"
    )
    @NotBlank(message = "El numero telefonico no puede estar vacio")
    private String telefono;

    @Schema(
            description = "Indica si el entrenador se encuentra activo en el gimnasio",
            example = "true"
    )
    @NotNull(message = "El activo no puede estar vacio")
    private Boolean activo;
}
