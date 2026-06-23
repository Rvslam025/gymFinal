package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar o modificar un producto del gimnasio")
public class ProductoRequest {

    @Schema(
            description = "Nombre del producto",
            example = "Proteína Whey Gold Standard"
    )
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(
            description = "Descripción detallada del producto",
            example = "Suplemento proteico de 2 lb sabor chocolate"
    )
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @Schema(
            description = "Precio del producto en pesos chilenos",
            example = "34990.0",
            minimum = "1"
    )
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Double precio;

    @Schema(
            description = "Cantidad de unidades disponibles en stock",
            example = "20",
            minimum = "0"
    )
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(
            description = "Indica si el producto se encuentra disponible para la venta",
            example = "true"
    )
    @NotNull(message = "Debe indicar disponibilidad")
    private Boolean disponible;

    @Schema(
            description = "Identificador único de la categoría a la que pertenece el producto",
            example = "1"
    )
    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;
}