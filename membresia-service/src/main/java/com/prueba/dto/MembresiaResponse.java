package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembresiaResponse {

    private Long id;
    private Long clienteId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activa;
    private String tipoMembresiaNombre;
    private Double precio;
    private Integer duracionDias;
}
