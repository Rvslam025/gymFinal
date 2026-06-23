package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Información de un pago registrado en el sistema")
public class PagoResponse {

    @Schema(
            description = "Identificador único del pago",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Monto total del pago realizado",
            example = "29990.0"
    )
    private double monto;

    @Schema(
            description = "Método utilizado para realizar el pago",
            example = "Tarjeta de Débito"
    )
    private String metodoPago;

    @Schema(
            description = "Estado actual del pago",
            example = "Pagado"
    )
    private String estado;

    @Schema(
            description = "Fecha en que se registró el pago",
            example = "2026-06-23T14:30:00.000+00:00"
    )
    private Date fechaPago;

    @Schema(
            description = "Identificador único de la venta asociada al pago",
            example = "5"
    )
    private Long ventaId;
}
