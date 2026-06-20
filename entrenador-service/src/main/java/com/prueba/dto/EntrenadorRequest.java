package com.prueba.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntrenadorRequest {

    @NotBlank(message = "El nombre no puede dejarlo vacio")
    private String nombre;

    @NotBlank(message = "La especialidad no puede estar vacia")
    private String especialidad;

    @NotBlank(message = "El numero telefonico no puede estar vacio")
    private String telefono;

    @NotNull(message = "El activo no puede estar vacio")
    private Boolean activo;

}
