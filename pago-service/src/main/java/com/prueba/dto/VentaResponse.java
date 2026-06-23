package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Información de una venta registrada en el sistema")
public class VentaResponse {

    @Schema(
            description = "Identificador único de la venta",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Fecha en la que se realizó la venta",
            example = "2026-06-23T15:30:00.000+00:00"
    )
    private Date fechaVenta;

    @Schema(
            description = "Cantidad de productos vendidos",
            example = "2"
    )
    private Integer cantidad;

    @Schema(
            description = "Monto total de la venta",
            example = "59980.0"
    )
    private double total;

    @Schema(
            description = "Identificador único del producto vendido",
            example = "3"
    )
    private Long productoId;

    @Schema(
            description = "Identificador único del cliente que realizó la compra",
            example = "1"
    )
    private Long clienteId;
}