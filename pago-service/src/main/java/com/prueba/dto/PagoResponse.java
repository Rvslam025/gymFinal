package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagoResponse {
    private Long id;
    private double monto;
    private String metodoPago;
    private String estado;
    private Date fechaPago;
    private Long ventaId;
}
