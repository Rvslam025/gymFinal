package com.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaseResponse {

    private Long id;
    private String nombre;
    private String instructor;
    private Integer capacidad;
    private Long entrenadorId;
    private List<HorarioResponse> horarios;
}
