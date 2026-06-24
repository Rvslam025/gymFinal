package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Información de un horario asociado a una clase")
public class HorarioResponse {

    @Schema(description = "Identificador único del horario",
            example = "1")
    private Long id;

    @Schema(description = "Día de la semana en que se realiza la clase",
            example = "MONDAY")
    private DayOfWeek diaSemana;

    @Schema(description = "Hora de inicio de la clase",
            example = "14:00:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora de finalización de la clase",
            example = "15:30:00")
    private LocalTime horaFin;
}
