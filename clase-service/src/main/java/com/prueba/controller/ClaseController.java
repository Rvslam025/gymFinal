package com.prueba.controller;

import com.prueba.dto.ClaseRequest;
import com.prueba.dto.ClaseResponse;
import com.prueba.service.ClaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @PostMapping
    public ResponseEntity<ClaseResponse> crearClase(@Valid @RequestBody ClaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(claseService.crearClase(request));
    }

    @GetMapping
    public ResponseEntity<List<ClaseResponse>> listarClases() {
        return ResponseEntity.ok(claseService.listarClases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(claseService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseResponse> modificarClase(@PathVariable Long id, @Valid @RequestBody ClaseRequest request) {
        return ResponseEntity.ok(claseService.modificarClase(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return ResponseEntity.noContent().build();
    }
}
