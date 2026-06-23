package com.prueba.controller;

import com.prueba.dto.PagoRequest;
import com.prueba.dto.PagoResponse;
import com.prueba.service.PagoService;
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
@RequestMapping("/api/pagos")
@Tag(
        name = "Pagos",
        description = "Operaciones relacionadas con la gestión de pagos de las membresías del gimnasio"
)
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(
            summary = "Registrar un pago",
            description = "Permite registrar un nuevo pago realizado por un cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
    })
    @PostMapping
    public ResponseEntity<PagoResponse> crear(
            @Valid @RequestBody PagoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.crear(request));
    }

    @Operation(
            summary = "Listar pagos",
            description = "Obtiene una lista de todos los pagos registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<PagoResponse>> obtenerPagos() {
        return ResponseEntity.ok(
                pagoService.obtenerPagos()
        );
    }

    @Operation(
            summary = "Buscar pago por ID",
            description = "Obtiene la información de un pago específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> obtenerPago(
            @Parameter(
                    description = "Identificador único del pago",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                pagoService.obtenerPago(id)
        );
    }

    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago registrado en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "Identificador único del pago a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
