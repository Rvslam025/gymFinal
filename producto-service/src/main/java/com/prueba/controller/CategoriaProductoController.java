package com.prueba.controller;

import com.prueba.model.CategoriaProducto;
import com.prueba.service.CategoriaProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
@Tag(
        name = "Categorías de Productos",
        description = "Operaciones relacionadas con la gestión de categorías de productos del gimnasio"
)
public class CategoriaProductoController {

    @Autowired
    private CategoriaProductoService categoriaService;

    @Operation(
            summary = "Crear una categoría",
            description = "Permite registrar una nueva categoría de productos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<CategoriaProducto>> crear(
            @RequestBody CategoriaProducto request
    ) {

        CategoriaProducto categoria = categoriaService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(categoria));
    }

    @Operation(
            summary = "Listar categorías",
            description = "Obtiene una lista de todas las categorías de productos registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CategoriaProducto>>> listar() {

        List<EntityModel<CategoriaProducto>> categorias =
                categoriaService.obtenerCategorias()
                        .stream()
                        .map(this::toModel)
                        .toList();

        CollectionModel<EntityModel<CategoriaProducto>> collection =
                CollectionModel.of(categorias);

        collection.add(linkTo(CategoriaProductoController.class).withSelfRel());
        collection.add(linkTo(CategoriaProductoController.class).withRel("crear"));

        return ResponseEntity.ok(collection);
    }

    @Operation(
            summary = "Buscar categoría por ID",
            description = "Obtiene la información de una categoría específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategoriaProducto>> obtener(
            @Parameter(
                    description = "Identificador único de la categoría",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                toModel(categoriaService.obtenerCategoria(id))
        );
    }

    @Operation(
            summary = "Eliminar categoría",
            description = "Elimina una categoría de productos del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(
                    description = "Identificador único de la categoría a eliminar",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {

        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<CategoriaProducto> toModel(CategoriaProducto categoria) {

        return EntityModel.of(
                categoria,

                linkTo(CategoriaProductoController.class)
                        .slash(categoria.getId())
                        .withSelfRel(),

                linkTo(CategoriaProductoController.class)
                        .withRel("categorias"),

                linkTo(CategoriaProductoController.class)
                        .slash(categoria.getId())
                        .withRel("eliminar")
        );
    }
}