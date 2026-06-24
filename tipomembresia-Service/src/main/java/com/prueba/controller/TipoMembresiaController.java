package com.prueba.controller;

import com.prueba.dto.TipoMembresiaRequest;
import com.prueba.dto.TipoMembresiaResponse;
import com.prueba.service.TipoMembresiaService;
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
@RequestMapping("/api/tipos-membresias")
@Tag(
        name = "Tipos de Membresías",
        description = "Operaciones relacionadas con la gestión de los tipos de membresías disponibles en el gimnasio"
)
public class TipoMembresiaController {

    @Autowired
    private TipoMembresiaService tipoMembresiaService;

    @Operation(
            summary = "Crear un tipo de membresía",
            description = "Permite registrar un nuevo tipo de membresía en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de membresía creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El tipo de membresía ya existe")
    })
    @PostMapping
    public ResponseEntity<EntityModel<TipoMembresiaResponse>> crear(
            @Valid @RequestBody TipoMembresiaRequest request
    ) {

        TipoMembresiaResponse tipoMembresia = tipoMembresiaService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(tipoMembresia));
    }

    @Operation(
            summary = "Listar tipos de membresías",
            description = "Obtiene una lista de todos los tipos de membresías registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TipoMembresiaResponse>>> obtenerTipoMembresias() {

        List<EntityModel<TipoMembresiaResponse>> tiposMembresias =
                tipoMembresiaService.obtenerTipoMembresias()
                        .stream()
                        .map(this::toModel)
                        .toList();

        CollectionModel<EntityModel<TipoMembresiaResponse>> collection =
                CollectionModel.of(tiposMembresias);

        collection.add(linkTo(TipoMembresiaController.class).withSelfRel());
        collection.add(linkTo(TipoMembresiaController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar tipo de membresía por ID",
            description = "Obtiene la información de un tipo de membresía específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de membresía encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de membresía no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TipoMembresiaResponse>> obtenerTipoMembresia(
            @Parameter(
                    description = "ID del tipo de membresía",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                toModel(tipoMembresiaService.obtenerTipoMembresia(id))
        );
    }

    @Operation(
            summary = "Modificar tipo de membresía",
            description = "Actualiza la información de un tipo de membresía existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de membresía actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Tipo de membresía no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TipoMembresiaResponse>> modificar(
            @Parameter(
                    description = "ID del tipo de membresía a modificar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody TipoMembresiaRequest request
    ) {

        return ResponseEntity.ok(
                toModel(tipoMembresiaService.modificar(id, request))
        );
    }

    @Operation(
            summary = "Eliminar tipo de membresía",
            description = "Elimina un tipo de membresía del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de membresía eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de membresía no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "ID del tipo de membresía a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        tipoMembresiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<TipoMembresiaResponse> toModel(TipoMembresiaResponse tipoMembresia) {

        return EntityModel.of(
                tipoMembresia,

                linkTo(TipoMembresiaController.class)
                        .slash(tipoMembresia.getId())
                        .withSelfRel(),

                linkTo(TipoMembresiaController.class)
                        .withRel("tipos-membresias"),

                linkTo(TipoMembresiaController.class)
                        .slash(tipoMembresia.getId())
                        .withRel("actualizar"),

                linkTo(TipoMembresiaController.class)
                        .slash(tipoMembresia.getId())
                        .withRel("eliminar")
        );
    }
}