package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoMembresiaResponse {

    private Long id;
    private String nombre; 
    private String descripcion;
    private Double precio;
    private Integer duracionDias;

}
