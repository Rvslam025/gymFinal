package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Información detallada de un producto incluido en una venta")
public class DetalleVentaResponse {

    @Schema(
            description = "Identificador único del detalle de la venta",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Identificador único del producto vendido",
            example = "3"
    )
    private Long productoId;

    @Schema(
            description = "Cantidad de unidades vendidas del producto",
            example = "2"
    )
    private Integer cantidad;

    @Schema(
            description = "Precio unitario del producto al momento de la venta",
            example = "14990.0"
    )
    private Double precioUnitario;

    @Schema(
            description = "Subtotal correspondiente al producto vendido",
            example = "29980.0"
    )
    private Double subtotal;
}
