package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta que representa una clase del gimnasio")
public class ClaseResponse {

    @Schema(description = "ID de la clase",
            example = "1")
    private Long id;

    @Schema(description = "Nombre de la clase",
            example = "Peso Muerto")
    private String nombre;

    @Schema(description = "Instructor de la clase",
            example = "Gonzalo Calderón")
    private String instructor;

    @Schema(description = "Capacidad máxima",
            example = "30")
    private Integer capacidad;

    @Schema(description = "ID del entrenador",
            example = "1")
    private Long entrenadorId;

    @ArraySchema(schema = @Schema(implementation = HorarioResponse.class))
    private List<HorarioResponse> horarios;
}

