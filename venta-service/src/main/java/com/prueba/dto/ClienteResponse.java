package com.prueba.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Date fechaNacimiento;
    private Boolean activo;
}
