package com.prueba.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TipoMembresiaRequest {

    @NotBlank(message = "El nombre no puede dejarlo vacio")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacia")
    private String descripcion;

    @NotNull(message = "El precio no puede estar vacio")
    private Double precio;

    @NotNull(message = "La duración en días no puede estar vacia")
    private Integer duracionDias;

    

}
