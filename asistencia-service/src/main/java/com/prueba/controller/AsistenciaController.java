package com.prueba.controller;

import com.prueba.DTO.AsistenciaRequest;
import com.prueba.DTO.AsistenciaResponse;
import com.prueba.service.AsistenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@Tag(
        name = "Asistencias",
        description = "Operaciones relacionadas con el registro de asistencias de los clientes del gimnasio"
)
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Operation(summary = "Registrar entrada",
            description = "Registra la hora de entrada de un cliente al gimnasio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrada registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PostMapping("/entrada")
    public ResponseEntity<AsistenciaResponse> registrarEntrada(
            @Valid @RequestBody AsistenciaRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(asistenciaService.registrarEntrada(request));
    }

    @Operation(summary = "Registrar salida",
            description = "Registra la hora de salida de una asistencia existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salida registrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @PutMapping("/salida/{id}")
    public ResponseEntity<AsistenciaResponse> registrarSalida(
            @Parameter(
                    description = "ID de la asistencia",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                asistenciaService.registrarSalida(id)
        );
    }

    @Operation(
            summary = "Listar asistencias",
            description = "Obtiene una lista de todas las asistencias registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<AsistenciaResponse>> listar() {
        return ResponseEntity.ok(
                asistenciaService.listarAsistencias()
        );
    }

    @Operation(
            summary = "Buscar asistencia por ID",
            description = "Obtiene la información de una asistencia específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asistencia encontrada"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaResponse> obtenerPorId(
            @Parameter(
                    description = "ID de la asistencia",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                asistenciaService.obtenerPorId(id)
        );
    }

    @Operation(
            summary = "Eliminar asistencia",
            description = "Elimina una asistencia del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asistencia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "ID de la asistencia a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        asistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}