package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Schema(description = "Horario de una clase")
public class HorarioRequest {

    @Schema(description = "Día de la semana de la clase",
            example = "MONDAY")
    private DayOfWeek diaSemana;

    @Schema(description = "Hora de inicio de la clase",
            example = "14:00:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora de término de la clase",
            example = "15:30:00")
    private LocalTime horaFin;
}
