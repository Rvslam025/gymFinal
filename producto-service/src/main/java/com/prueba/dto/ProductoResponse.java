package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Información de un producto registrado en el sistema")
public class ProductoResponse {

    @Schema(
            description = "Identificador único del producto",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del producto",
            example = "Proteína Whey Gold Standard"
    )
    private String nombre;

    @Schema(
            description = "Descripción detallada del producto",
            example = "Suplemento proteico de 2 lb sabor chocolate"
    )
    private String descripcion;

    @Schema(
            description = "Precio del producto en pesos chilenos",
            example = "34990.0"
    )
    private double precio;

    @Schema(
            description = "Cantidad de unidades disponibles en stock",
            example = "20"
    )
    private Integer stock;

    @Schema(
            description = "Indica si el producto está disponible para la venta",
            example = "true"
    )
    private boolean disponible;

    @Schema(
            description = "Nombre de la categoría a la que pertenece el producto",
            example = "Suplementos"
    )
    private String categoriaNombre;
}
