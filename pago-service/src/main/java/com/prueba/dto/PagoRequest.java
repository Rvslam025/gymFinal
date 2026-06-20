package com.prueba.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequest {

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "Debe ingresar el id de la venta")
    private Long ventaId;
}
