package com.prueba.controller;

import com.prueba.model.CategoriaProducto;
import com.prueba.service.CategoriaProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categorias")
public class CategoriaProductoController {

    @Autowired
    private CategoriaProductoService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaProducto> crear(@RequestBody CategoriaProducto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> listar() {
        return ResponseEntity.ok(categoriaService.obtenerCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerCategoria(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
