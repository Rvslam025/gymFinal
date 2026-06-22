package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Datos necesarios para crear o modificar una clase del gimnasio")
public class ClaseRequest {

    @Schema(description = "Nombre de la clase", example = "Crossfit")

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Nombre del instructor que impartirá la clase",
            example = "Gonzalo Calderón")

    @NotBlank(message = "El instructor es obligatorio")
    private String instructor;

    @Schema(description = "Capacidad máxima de alumnos permitidos en la clase",
            example = "30")

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private Integer capacidad;

    @Schema(description = "ID del entrenador asociado a la clase", example = "1")

    @NotNull(message = "El entrenador es obligatorio")
    private Long entrenadorId;

    @ArraySchema(schema = @Schema(implementation = HorarioRequest.class,
            description = "Lista de horarios asociados a la clase"))

    private List<HorarioRequest> horarios;
}
