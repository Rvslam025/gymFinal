package com.prueba.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ReservaRequest {

    @NotNull(message = "La fecha de reserva no puede estar vacía")
    private Date fechaReserva;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "La clase es obligatoria")
    private Long claseId;
}