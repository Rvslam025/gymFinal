package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar un pago asociado a una venta")
public class PagoRequest {

    @Schema(
            description = "Método utilizado para realizar el pago",
            example = "Débito"
    )
    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    @Schema(
            description = "Estado actual del pago",
            example = "Pendiente"
    )
    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @Schema(
            description = "Identificador único de la venta asociada al pago",
            example = "1"
    )
    @NotNull(message = "Debe ingresar el id de la venta")
    private Long ventaId;
}
