package com.prueba.controller;

import com.prueba.dto.MembresiaRequest;
import com.prueba.dto.MembresiaResponse;
import com.prueba.service.MembresiaService;
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
@RequestMapping("/api/membresias")
@Tag(
        name = "Membresías",
        description = "Operaciones relacionadas con la gestión de membresías de los clientes del gimnasio"
)
public class MembresiaController {

    @Autowired
    private MembresiaService membresiaService;

    @Operation(
            summary = "Crear una membresía",
            description = "Permite registrar una nueva membresía para un cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Membresía creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "La membresía ya existe")
    })
    @PostMapping
    public ResponseEntity<MembresiaResponse> crear(
            @Valid @RequestBody MembresiaRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membresiaService.crear(request));
    }

    @Operation(
            summary = "Listar membresías",
            description = "Obtiene una lista de todas las membresías registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<MembresiaResponse>> listar() {
        return ResponseEntity.ok(
                membresiaService.listar()
        );
    }

    @Operation(
            summary = "Buscar membresía por ID",
            description = "Obtiene la información de una membresía específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membresía encontrada"),
            @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MembresiaResponse> obtenerPorId(
            @Parameter(
                    description = "ID de la membresía",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                membresiaService.obtenerPorId(id)
        );
    }

    @Operation(
            summary = "Modificar membresía",
            description = "Actualiza la información de una membresía existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Membresía actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MembresiaResponse> modificar(
            @Parameter(
                    description = "ID de la membresía a modificar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody MembresiaRequest request
    ) {
        return ResponseEntity.ok(
                membresiaService.modificar(id, request)
        );
    }

    @Operation(
            summary = "Eliminar membresía",
            description = "Elimina una membresía del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Membresía eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "ID de la membresía a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        membresiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Validar membresía activa",
            description = "Verifica si un cliente posee una membresía actualmente activa"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validación realizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/activa/{clienteId}")
    public ResponseEntity<Boolean> validarActiva(
            @Parameter(
                    description = "ID del cliente",
                    example = "1",
                    required = true
            )
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(
                membresiaService.validarActiva(clienteId)
        );
    }
}
