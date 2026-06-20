package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorResponse {

    private Long id;
    private String nombre;
    private String especialidad;
    private String telefono;
    private Boolean activo;

}
