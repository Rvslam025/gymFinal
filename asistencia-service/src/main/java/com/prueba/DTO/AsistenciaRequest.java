package com.prueba.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar la entrada de un cliente al gimnasio")
public class AsistenciaRequest {

    @Schema(description = "Nombre del cliente que registra la asistencia",
            example = "Juan Pérez")

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(description = "ID del cliente asociado a la asistencia. Es opcional y se utiliza cuando la asistencia está vinculada a un cliente registrado.",
            example = "1",
            nullable = true)

    private Long clienteId;
}
