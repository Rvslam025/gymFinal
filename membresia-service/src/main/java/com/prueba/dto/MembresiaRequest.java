package com.prueba.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MembresiaRequest {

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    @NotNull(message = "El tipo de membresía es obligatorio")
    private Long tipoMembresiaId;
}
