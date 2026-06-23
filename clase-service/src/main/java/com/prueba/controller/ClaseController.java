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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/clases")
@Tag(name = "Clases", description = "Operaciones relacionadas con las clases del gimnasio")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @Operation(
            summary = "Crear una nueva clase",
            description = "Permite registrar una nueva clase del gimnasio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clase creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "409", description = "La clase ya existe")
    })
    @PostMapping
    public ResponseEntity<EntityModel<ClaseResponse>> crearClase(@Valid @RequestBody ClaseRequest request) {
        ClaseResponse clase = claseService.crearClase(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(clase));
    }

    @Operation(
            summary = "Listar todas las clases",
            description = "Obtiene una lista de todas las clases registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacion exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ClaseResponse>>> listarClases() {
        List<EntityModel<ClaseResponse>> clases = claseService.listarClases()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<ClaseResponse>> collection = CollectionModel.of(clases);
        collection.add(linkTo(methodOn(ClaseController.class).listarClases()).withSelfRel());
        collection.add(linkTo(methodOn(ClaseController.class).crearClase(null)).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar una clase por ID",
            description = "Obtiene la informacion de una clase especifica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clase encontrada"),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClaseResponse>> obtenerPorId(
            @Parameter(description = "ID de la clase", required = true, example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(toModel(claseService.obtenerPorId(id)));
    }

    @Operation(
            summary = "Modificar una clase",
            description = "Actualiza los datos de una clase existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clase modificada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClaseResponse>> modificarClase(
            @Parameter(description = "ID de la clase a modificar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ClaseRequest request) {

        return ResponseEntity.ok(toModel(claseService.modificarClase(id, request)));
    }

    @Operation(
            summary = "Eliminar una clase",
            description = "Elimina una clase del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Clase eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(
            @Parameter(description = "ID de la clase a eliminar", required = true, example = "1")
            @PathVariable Long id) {

        claseService.eliminarClase(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ClaseResponse> toModel(ClaseResponse clase) {
        return EntityModel.of(clase,
                linkTo(methodOn(ClaseController.class).obtenerPorId(clase.getId())).withSelfRel(),
                linkTo(methodOn(ClaseController.class).listarClases()).withRel("clases"),
                linkTo(methodOn(ClaseController.class).modificarClase(clase.getId(), null)).withRel("actualizar"),
                linkTo(methodOn(ClaseController.class).eliminarClase(clase.getId())).withRel("eliminar"));
    }
}
