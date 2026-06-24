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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PostMapping
    public ResponseEntity<EntityModel<PagoResponse>> crear(
            @Valid @RequestBody PagoRequest request
    ) {

        PagoResponse pago = pagoService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(pago));
    }

    @Operation(
            summary = "Listar pagos",
            description = "Obtiene una lista de todos los pagos registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PagoResponse>>> obtenerPagos() {

        List<EntityModel<PagoResponse>> pagos = pagoService.obtenerPagos()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<PagoResponse>> collection =
                CollectionModel.of(pagos);

        collection.add(linkTo(PagoController.class).withSelfRel());
        collection.add(linkTo(PagoController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
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
    public ResponseEntity<EntityModel<PagoResponse>> obtenerPago(
            @Parameter(
                    description = "Identificador único del pago",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                toModel(pagoService.obtenerPago(id))
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

    private EntityModel<PagoResponse> toModel(PagoResponse pago) {

        return EntityModel.of(
                pago,

                linkTo(PagoController.class)
                        .slash(pago.getId())
                        .withSelfRel(),

                linkTo(PagoController.class)
                        .withRel("pagos"),

                linkTo(PagoController.class)
                        .slash(pago.getId())
                        .withRel("eliminar")
        );
    }
}