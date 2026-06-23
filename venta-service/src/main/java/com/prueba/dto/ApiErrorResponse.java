package com.prueba.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta estándar de error de la API")
public class ApiErrorResponse {

    @Schema(description = "Fecha y hora en que ocurrió el error",
            example = "2026-06-22T16:30:45")
    private LocalDateTime timestamp;

    @Schema(description = "Código HTTP del error",
            example = "404")
    private int status;

    @Schema(description = "Nombre del estado HTTP",
            example = "NOT_FOUND")
    private String error;

    @Schema(description = "Mensaje descriptivo del error",
            example = "Venta no encontrada")
    private String message;

    @Schema(description = "Ruta del endpoint donde ocurrió el error",
            example = "/api/clases/10")
    private String path;

    @ArraySchema(schema = @Schema(description = "Lista de errores de validación",
            example = "nombre: El nombre es obligatorio"))
    private List<String> errors;
}