package com.prueba.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaRequest {

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "Debe ingresar el id del producto")
    private Long productoId;

    @NotNull(message = "Debe ingresar el id del cliente")
    private Long clienteId;
}