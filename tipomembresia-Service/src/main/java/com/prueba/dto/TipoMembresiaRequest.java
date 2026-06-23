package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar o modificar un tipo de membresía")
public class TipoMembresiaRequest {

    @Schema(
            description = "Nombre del tipo de membresía",
            example = "Mensual"
    )
    @NotBlank(message = "El nombre no puede dejarlo vacio")
    private String nombre;

    @Schema(
            description = "Descripción del tipo de membresía y sus beneficios",
            example = "Acceso ilimitado al gimnasio durante 30 días"
    )
    @NotBlank(message = "La descripción no puede estar vacia")
    private String descripcion;

    @Schema(
            description = "Precio de la membresía en pesos chilenos",
            example = "29990.0",
            minimum = "0"
    )
    @NotNull(message = "El precio no puede estar vacio")
    private Double precio;

    @Schema(
            description = "Duración de la membresía expresada en días",
            example = "30",
            minimum = "1"
    )
    @NotNull(message = "La duración en días no puede estar vacia")
    private Integer duracionDias;
}
