package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar una venta de productos")
public class VentaRequest {

    @Schema(
            description = "Cantidad de unidades del producto que se desean vender",
            example = "2",
            minimum = "1"
    )
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @Schema(
            description = "Identificador único del producto que se venderá",
            example = "5"
    )
    @NotNull(message = "Debe ingresar el id del producto")
    private Long productoId;

    @Schema(
            description = "Identificador único del cliente que realiza la compra",
            example = "1"
    )
    @NotNull(message = "Debe ingresar el id del cliente")
    private Long clienteId;
}