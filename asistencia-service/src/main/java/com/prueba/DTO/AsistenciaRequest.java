package com.prueba.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AsistenciaRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    // clienteId opcional — permite vincular al cliente si se conoce
    private Long clienteId;
}
