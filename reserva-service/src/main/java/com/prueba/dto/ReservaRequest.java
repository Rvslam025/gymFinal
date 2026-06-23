package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Datos necesarios para registrar o modificar una reserva de una clase")
public class ReservaRequest {

    @Schema(
            description = "Fecha en la que se realiza la reserva",
            example = "2026-06-23T10:30:00.000+00:00"
    )
    @NotNull(message = "La fecha de reserva no puede estar vacía")
    private Date fechaReserva;

    @Schema(
            description = "Estado actual de la reserva",
            example = "PENDIENTE"
    )
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @Schema(
            description = "Identificador único del cliente que realiza la reserva",
            example = "1"
    )
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @Schema(
            description = "Identificador único de la clase reservada",
            example = "2"
    )
    @NotNull(message = "La clase es obligatoria")
    private Long claseId;
}