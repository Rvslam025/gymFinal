package com.prueba.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ClaseRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El instructor es obligatorio")
    private String instructor;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @NotNull(message = "El entrenador es obligatorio")
    private Long entrenadorId;

    private List<HorarioRequest> horarios;
}
