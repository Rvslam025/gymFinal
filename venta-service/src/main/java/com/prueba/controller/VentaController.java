package com.prueba.controller;

import com.prueba.dto.VentaRequest;
import com.prueba.dto.VentaResponse;
import com.prueba.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/ventas")
@Tag(
        name = "Ventas",
        description = "Operaciones relacionadas con la gestión de ventas de productos del gimnasio"
)
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(
            summary = "Crear una venta",
            description = "Permite registrar una nueva venta de productos realizada por un cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente o producto no encontrado"),
            @ApiResponse(responseCode = "409", description = "Stock insuficiente para realizar la venta")
    })
    @PostMapping
    public ResponseEntity<EntityModel<VentaResponse>> crear(
            @Valid @RequestBody VentaRequest request
    ) {

        VentaResponse venta = ventaService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(venta));
    }

    @Operation(
            summary = "Listar ventas",
            description = "Obtiene una lista de todas las ventas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<VentaResponse>>> obtenerVentas() {

        List<EntityModel<VentaResponse>> ventas = ventaService.obtenerVentas()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<VentaResponse>> collection =
                CollectionModel.of(ventas);

        collection.add(linkTo(VentaController.class).withSelfRel());
        collection.add(linkTo(VentaController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar venta por ID",
            description = "Obtiene la información de una venta específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<VentaResponse>> obtenerVenta(
            @Parameter(
                    description = "Identificador único de la venta",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                toModel(ventaService.obtenerVenta(id))
        );
    }

    @Operation(
            summary = "Eliminar venta",
            description = "Elimina una venta registrada en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "Identificador único de la venta a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<VentaResponse> toModel(VentaResponse venta) {

        return EntityModel.of(
                venta,

                linkTo(VentaController.class)
                        .slash(venta.getId())
                        .withSelfRel(),

                linkTo(VentaController.class)
                        .withRel("ventas"),

                linkTo(VentaController.class)
                        .slash(venta.getId())
                        .withRel("eliminar")
        );
    }
}