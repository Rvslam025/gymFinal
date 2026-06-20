package com.prueba.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ClienteRequest {

    @NotBlank(message = "El nombre no puede dejarlo vacio")
    private String nombre;

    @NotBlank(message = "El apellido no puede dejarlo vacio")
    private String apellido;

    @NotBlank(message = "El correo no puede dejarlo vacio")
    @Email(message = "El formato del correo no es válido")
    private String email;

    @NotBlank(message = "El telefono no puede dejarlo vacio")
    private String telefono;

    @NotNull(message = "La fecha de nacimiento no puede dejarlo vacio")
    private Date fechaNacimiento;

    @NotNull(message = "El activo no puede dejarlo vacio")
    private Boolean activo;
}
