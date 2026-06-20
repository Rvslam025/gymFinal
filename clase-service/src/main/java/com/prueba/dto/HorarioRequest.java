package com.prueba.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class HorarioRequest {

    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
