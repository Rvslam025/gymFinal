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
@RequestMapping("/api/clientes")
@Tag(
        name = "Clientes",
        description = "Operaciones relacionadas con la gestion de clientes del gimnasio"
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
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "409", description = "El cliente ya existe")
    })
    @PostMapping
    public ResponseEntity<EntityModel<ClienteResponse>> crear(
            @Valid @RequestBody ClienteRequest request) {

        ClienteResponse cliente = clienteService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(cliente));
    }

    @Operation(
            summary = "Listar clientes",
            description = "Obtiene una lista de todos los clientes registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operacion exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ClienteResponse>>> obtenerClientes() {
        List<EntityModel<ClienteResponse>> clientes = clienteService.obtenerClientes()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<ClienteResponse>> collection = CollectionModel.of(clientes);
        collection.add(linkTo(ClienteController.class).withSelfRel());
        collection.add(linkTo(ClienteController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Obtiene la informacion de un cliente especifico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteResponse>> obtenerCliente(
            @Parameter(description = "ID del cliente", example = "1", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(toModel(clienteService.obtenerCliente(id)));
    }

    @Operation(
            summary = "Modificar cliente",
            description = "Actualiza la informacion de un cliente existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteResponse>> modificar(
            @Parameter(description = "ID del cliente a modificar", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {

        return ResponseEntity.ok(toModel(clienteService.modificar(id, request)));
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
            @Parameter(description = "ID del cliente a eliminar", example = "1", required = true)
            @PathVariable Long id) {

        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ClienteResponse> toModel(ClienteResponse cliente) {
        EntityModel<ClienteResponse> model = EntityModel.of(cliente,
                linkTo(ClienteController.class).slash(cliente.getId()).withSelfRel(),
                linkTo(ClienteController.class).withRel("clientes"),
                linkTo(ClienteController.class).slash(cliente.getId()).withRel("actualizar"),
                linkTo(ClienteController.class).slash(cliente.getId()).withRel("eliminar"));

        if (Boolean.TRUE.equals(cliente.getActivo())) {
            model.add(linkTo(ClienteController.class)
                    .slash(cliente.getId())
                    .withRel("desactivar"));
        }

        return model;
    }
}
