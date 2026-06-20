package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaResponse {
    private Long id;
    private Date fechaVenta;
    private Integer cantidad;
    private double total;
    private Long productoId;
    private Long clienteId;
    private List<DetalleVentaResponse> detalles;
}
