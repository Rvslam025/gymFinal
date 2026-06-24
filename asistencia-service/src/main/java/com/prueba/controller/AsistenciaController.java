package com.prueba.controller;

import com.prueba.DTO.AsistenciaRequest;
import com.prueba.DTO.AsistenciaResponse;
import com.prueba.service.AsistenciaService;
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

@RestController
@RequestMapping("/api/asistencias")
@Tag(
        name = "Asistencias",
        description = "Operaciones relacionadas con el registro de asistencias de los clientes del gimnasio"
)
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Operation(
            summary = "Registrar entrada",
            description = "Registra la hora de entrada de un cliente al gimnasio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrada registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PostMapping("/entrada")
    public ResponseEntity<EntityModel<AsistenciaResponse>> registrarEntrada(
            @Valid @RequestBody AsistenciaRequest request) {

        AsistenciaResponse asistencia = asistenciaService.registrarEntrada(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(asistencia));
    }

    @Operation(
            summary = "Registrar salida",
            description = "Registra la hora de salida de una asistencia existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salida registrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @PutMapping("/salida/{id}")
    public ResponseEntity<EntityModel<AsistenciaResponse>> registrarSalida(
            @Parameter(description = "ID de la asistencia", example = "1", required = true)
            @PathVariable Long id) {

        AsistenciaResponse asistencia = asistenciaService.registrarSalida(id);
        return ResponseEntity.ok(toModel(asistencia));
    }

    @Operation(
            summary = "Listar asistencias",
            description = "Obtiene una lista de todas las asistencias registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacion exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<AsistenciaResponse>>> listar() {
        List<EntityModel<AsistenciaResponse>> asistencias = asistenciaService.listarAsistencias()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<AsistenciaResponse>> collection = CollectionModel.of(asistencias);
        collection.add(linkTo(AsistenciaController.class).withSelfRel());
        collection.add(linkTo(AsistenciaController.class).slash("entrada").withRel("registrar-entrada"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar asistencia por ID",
            description = "Obtiene la informacion de una asistencia especifica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asistencia encontrada"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AsistenciaResponse>> obtenerPorId(
            @Parameter(description = "ID de la asistencia", example = "1", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(toModel(asistenciaService.obtenerPorId(id)));
    }

    @Operation(
            summary = "Eliminar asistencia",
            description = "Elimina una asistencia del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asistencia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la asistencia a eliminar", example = "1", required = true)
            @PathVariable Long id) {

        asistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<AsistenciaResponse> toModel(AsistenciaResponse asistencia) {
        EntityModel<AsistenciaResponse> model = EntityModel.of(asistencia,
                linkTo(AsistenciaController.class).slash(asistencia.getId()).withSelfRel(),
                linkTo(AsistenciaController.class).withRel("asistencias"),
                linkTo(AsistenciaController.class).slash("salida").slash(asistencia.getId()).withRel("registrar-salida"),
                linkTo(AsistenciaController.class).slash(asistencia.getId()).withRel("eliminar"));

        if (asistencia.getRegistroSalida() == null) {
            model.add(linkTo(AsistenciaController.class)
                    .slash("salida")
                    .slash(asistencia.getId())
                    .withRel("marcar-salida-pendiente"));
        }

        return model;
    }
}