package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Datos necesarios para registrar o modificar un cliente del gimnasio")
public class ClienteRequest {

    @Schema(
            description = "Nombre del cliente",
            example = "Juan"
    )
    @NotBlank(message = "El nombre no puede dejarlo vacio")
    private String nombre;

    @Schema(
            description = "Apellido del cliente",
            example = "Pérez"
    )
    @NotBlank(message = "El apellido no puede dejarlo vacio")
    private String apellido;

    @Schema(
            description = "Correo electrónico del cliente",
            example = "juan.perez@gmail.com"
    )
    @NotBlank(message = "El correo no puede dejarlo vacio")
    @Email(message = "El formato del correo no es válido")
    private String email;

    @Schema(
            description = "Número de teléfono del cliente",
            example = "+56912345678"
    )
    @NotBlank(message = "El telefono no puede dejarlo vacio")
    private String telefono;

    @Schema(
            description = "Fecha de nacimiento del cliente",
            example = "2000-01-15"
    )
    @NotNull(message = "La fecha de nacimiento no puede dejarlo vacio")
    private Date fechaNacimiento;

    @Schema(
            description = "Indica si el cliente se encuentra activo en el gimnasio",
            example = "true"
    )
    @NotNull(message = "El activo no puede dejarlo vacio")
    private Boolean activo;
}
