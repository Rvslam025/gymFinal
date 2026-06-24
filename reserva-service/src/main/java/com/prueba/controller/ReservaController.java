package com.prueba.controller;

import com.prueba.dto.ReservaRequest;
import com.prueba.dto.ReservaResponse;
import com.prueba.service.ReservaService;
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
@RequestMapping("/api/reservas")
@Tag(
        name = "Reservas",
        description = "Operaciones relacionadas con la gestión de reservas de clases del gimnasio"
)
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(
            summary = "Crear una reserva",
            description = "Permite registrar una nueva reserva de una clase para un cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente o clase no encontrada"),
            @ApiResponse(responseCode = "409", description = "La reserva ya existe o no hay cupos disponibles")
    })
    @PostMapping
    public ResponseEntity<EntityModel<ReservaResponse>> crear(
            @Valid @RequestBody ReservaRequest request
    ) {

        ReservaResponse reserva = reservaService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(reserva));
    }

    @Operation(
            summary = "Listar reservas",
            description = "Obtiene una lista de todas las reservas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservaResponse>>> obtenerReservas() {

        List<EntityModel<ReservaResponse>> reservas = reservaService.obtenerReservas()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<ReservaResponse>> collection =
                CollectionModel.of(reservas);

        collection.add(linkTo(ReservaController.class).withSelfRel());
        collection.add(linkTo(ReservaController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar reserva por ID",
            description = "Obtiene la información de una reserva específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReservaResponse>> obtenerReserva(
            @Parameter(
                    description = "Identificador único de la reserva",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                toModel(reservaService.obtenerReserva(id))
        );
    }

    @Operation(
            summary = "Modificar reserva",
            description = "Actualiza la información de una reserva existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReservaResponse>> modificar(
            @Parameter(
                    description = "Identificador único de la reserva a modificar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequest request
    ) {

        return ResponseEntity.ok(
                toModel(reservaService.modificar(id, request))
        );
    }

    @Operation(
            summary = "Eliminar reserva",
            description = "Elimina una reserva registrada en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "Identificador único de la reserva a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ReservaResponse> toModel(ReservaResponse reserva) {

        return EntityModel.of(
                reserva,

                linkTo(ReservaController.class)
                        .slash(reserva.getId())
                        .withSelfRel(),

                linkTo(ReservaController.class)
                        .withRel("reservas"),

                linkTo(ReservaController.class)
                        .slash(reserva.getId())
                        .withRel("actualizar"),

                linkTo(ReservaController.class)
                        .slash(reserva.getId())
                        .withRel("eliminar")
        );
    }
}