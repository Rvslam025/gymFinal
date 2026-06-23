package com.prueba.controller;

import com.prueba.dto.ClienteRequest;
import com.prueba.dto.ClienteResponse;
import com.prueba.service.ClienteService;
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
@RequestMapping("/api/clientes")
@Tag(
        name = "Clientes",
        description = "Operaciones relacionadas con la gestión de clientes del gimnasio"
)
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
            summary = "Crear un cliente",
            description = "Permite registrar un nuevo cliente en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El cliente ya existe")
    })
    @PostMapping
    public ResponseEntity<ClienteResponse> crear(
            @Valid @RequestBody ClienteRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crear(request));
    }

    @Operation(
            summary = "Listar clientes",
            description = "Obtiene una lista de todos los clientes registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> obtenerClientes() {
        return ResponseEntity.ok(
                clienteService.obtenerClientes()
        );
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Obtiene la información de un cliente específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerCliente(
            @Parameter(
                    description = "ID del cliente",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                clienteService.obtenerCliente(id)
        );
    }

    @Operation(
            summary = "Modificar cliente",
            description = "Actualiza la información de un cliente existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> modificar(
            @Parameter(
                    description = "ID del cliente a modificar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request
    ) {
        return ResponseEntity.ok(
                clienteService.modificar(id, request)
        );
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "ID del cliente a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
