package com.prueba.controller;

import com.prueba.dto.ProductoRequest;
import com.prueba.dto.ProductoResponse;
import com.prueba.service.ProductoService;
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
@RequestMapping("/api/productos")
@Tag(
        name = "Productos",
        description = "Operaciones relacionadas con la gestión de productos del gimnasio"
)
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(
            summary = "Crear un producto",
            description = "Permite registrar un nuevo producto en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El producto ya existe")
    })
    @PostMapping
    public ResponseEntity<EntityModel<ProductoResponse>> crear(
            @Valid @RequestBody ProductoRequest request
    ) {

        ProductoResponse producto = productoService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(producto));
    }

    @Operation(
            summary = "Listar productos",
            description = "Obtiene una lista de todos los productos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductoResponse>>> obtenerProductos() {

        List<EntityModel<ProductoResponse>> productos = productoService.obtenerProductos()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<ProductoResponse>> collection =
                CollectionModel.of(productos);

        collection.add(linkTo(ProductoController.class).withSelfRel());
        collection.add(linkTo(ProductoController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar producto por ID",
            description = "Obtiene la información de un producto específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoResponse>> obtenerProducto(
            @Parameter(
                    description = "Identificador único del producto",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                toModel(productoService.obtenerProducto(id))
        );
    }

    @Operation(
            summary = "Modificar producto",
            description = "Actualiza la información de un producto existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoResponse>> modificar(
            @Parameter(
                    description = "Identificador único del producto a modificar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request
    ) {

        return ResponseEntity.ok(
                toModel(productoService.modificar(id, request))
        );
    }

    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto registrado en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "Identificador único del producto a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ProductoResponse> toModel(ProductoResponse producto) {

        return EntityModel.of(
                producto,

                linkTo(ProductoController.class)
                        .slash(producto.getId())
                        .withSelfRel(),

                linkTo(ProductoController.class)
                        .withRel("productos"),

                linkTo(ProductoController.class)
                        .slash(producto.getId())
                        .withRel("actualizar"),

                linkTo(ProductoController.class)
                        .slash(producto.getId())
                        .withRel("eliminar")
        );
    }
}