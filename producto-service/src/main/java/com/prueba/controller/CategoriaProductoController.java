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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CategoriaProducto> crear(
            @RequestBody CategoriaProducto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.crear(request));
    }

    @Operation(
            summary = "Listar categorías",
            description = "Obtiene una lista de todas las categorías de productos registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> listar() {
        return ResponseEntity.ok(
                categoriaService.obtenerCategorias()
        );
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
    public ResponseEntity<CategoriaProducto> obtener(
            @Parameter(
                    description = "Identificador único de la categoría",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                categoriaService.obtenerCategoria(id)
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
}
