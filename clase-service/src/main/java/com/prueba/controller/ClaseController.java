package com.prueba.controller;

import com.prueba.dto.ClaseRequest;
import com.prueba.dto.ClaseResponse;
import com.prueba.service.ClaseService;
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
@RequestMapping("/api/clases")
@Tag(name = "Clases", description = "Operaciones relacionadas con las clases del gimnasio")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @Operation(summary = "Crear una nueva clase",
            description = "Permite registrar una nueva clase del gimnasio")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clase creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "La clase ya existe")
    })

    @PostMapping
    public ResponseEntity<ClaseResponse> crearClase(@Valid @RequestBody ClaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(claseService.crearClase(request));
    }

    @Operation(summary = "Listar todas las clases",
            description = "Obtiene una lista de todas las clases registradas")

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Operación exitosa")})

    @GetMapping
    public ResponseEntity<List<ClaseResponse>> listarClases() {return ResponseEntity.ok(claseService.listarClases());}

    @Operation(
            summary = "Buscar una clase por ID",
            description = "Obtiene la información de una clase específica")

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Clase encontrada"),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada")})

    @GetMapping("/{id}")
    public ResponseEntity<ClaseResponse> obtenerPorId(@Parameter(description = "ID de la clase", required = true, example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(claseService.obtenerPorId(id));}

    @Operation(summary = "Modificar una clase",
            description = "Actualiza los datos de una clase existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clase modificada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada")})

    @PutMapping("/{id}")
    public ResponseEntity<ClaseResponse> modificarClase(@Parameter(
                    description = "ID de la clase a modificar",
                    required = true,
                    example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ClaseRequest request) {

        return ResponseEntity.ok(claseService.modificarClase(id, request));
    }

    @Operation(summary = "Eliminar una clase", description = "Elimina una clase del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Clase eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada")})

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(
            @Parameter(description = "ID de la clase a eliminar",
                    required = true,
                    example = "1")
            @PathVariable Long id) {claseService.eliminarClase(id);return ResponseEntity.noContent().build();
    }
}
