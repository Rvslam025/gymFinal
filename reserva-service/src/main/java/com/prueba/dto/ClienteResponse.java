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
@Schema(description = "Información de un cliente registrado en el gimnasio")
public class ClienteResponse {

    @Schema(
            description = "Identificador único del cliente",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del cliente",
            example = "Juan"
    )
    private String nombre;

    @Schema(
            description = "Apellido del cliente",
            example = "Pérez"
    )
    private String apellido;

    @Schema(
            description = "Correo electrónico del cliente",
            example = "juan.perez@gmail.com"
    )
    private String email;

    @Schema(
            description = "Número de teléfono del cliente",
            example = "+56912345678"
    )
    private String telefono;

    @Schema(
            description = "Fecha de nacimiento del cliente",
            example = "2000-01-15"
    )
    private Date fechaNacimiento;

    @Schema(
            description = "Indica si el cliente se encuentra activo en el gimnasio",
            example = "true"
    )
    private Boolean activo;
}
