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
public class ClienteResponse {

    private Long id;
    private String nombre;
    private String apellido; 
    private String email;
    private String telefono;
    private Date fechaNacimiento;
    private Boolean activo;
}
