package com.prueba.dto;

import lombok.Data;
import java.util.Date;

@Data
public class VentaResponse {
    private Long id;
    private Date fechaVenta;
    private Integer cantidad;
    private double total;
    private Long productoId;
    private Long clienteId;
}
