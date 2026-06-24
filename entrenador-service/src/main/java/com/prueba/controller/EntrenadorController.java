package com.prueba.controller;

import com.prueba.dto.EntrenadorRequest;
import com.prueba.dto.EntrenadorResponse;
import com.prueba.service.EntrenadorService;
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
@RequestMapping("/api/entrenadores")
@Tag(
        name = "Entrenadores",
        description = "Operaciones relacionadas con la gestión de entrenadores del gimnasio"
)
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @Operation(
            summary = "Crear un entrenador",
            description = "Permite registrar un nuevo entrenador en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrenador creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El entrenador ya existe")
    })
    @PostMapping
    public ResponseEntity<EntityModel<EntrenadorResponse>> crear(
            @Valid @RequestBody EntrenadorRequest request
    ) {

        EntrenadorResponse entrenador = entrenadorService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(entrenador));
    }

    @Operation(
            summary = "Listar entrenadores",
            description = "Obtiene una lista de todos los entrenadores registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EntrenadorResponse>>> obtenerEntrenadores() {

        List<EntityModel<EntrenadorResponse>> entrenadores = entrenadorService.obtenerEntrenadores()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<EntrenadorResponse>> collection =
                CollectionModel.of(entrenadores);

        collection.add(linkTo(EntrenadorController.class).withSelfRel());
        collection.add(linkTo(EntrenadorController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar entrenador por ID",
            description = "Obtiene la información de un entrenador específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrenador encontrado"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EntrenadorResponse>> obtenerEntrenador(
            @Parameter(
                    description = "ID del entrenador",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                toModel(entrenadorService.obtenerEntrenador(id))
        );
    }

    @Operation(
            summary = "Modificar entrenador",
            description = "Actualiza la información de un entrenador existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrenador actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EntrenadorResponse>> modificar(
            @Parameter(
                    description = "ID del entrenador a modificar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody EntrenadorRequest request
    ) {
        return ResponseEntity.ok(
                toModel(entrenadorService.modificar(id, request))
        );
    }

    @Operation(
            summary = "Eliminar entrenador",
            description = "Elimina un entrenador del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrenador eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "ID del entrenador a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        entrenadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<EntrenadorResponse> toModel(EntrenadorResponse entrenador) {

        EntityModel<EntrenadorResponse> model = EntityModel.of(
                entrenador,
                linkTo(EntrenadorController.class)
                        .slash(entrenador.getId())
                        .withSelfRel(),

                linkTo(EntrenadorController.class)
                        .withRel("entrenadores"),

                linkTo(EntrenadorController.class)
                        .slash(entrenador.getId())
                        .withRel("actualizar"),

                linkTo(EntrenadorController.class)
                        .slash(entrenador.getId())
                        .withRel("eliminar")
        );

        if (Boolean.TRUE.equals(entrenador.getActivo())) {
            model.add(
                    linkTo(EntrenadorController.class)
                            .slash(entrenador.getId())
                            .withRel("desactivar")
            );
        }

        return model;
    }
}

