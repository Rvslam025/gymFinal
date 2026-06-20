package com.prueba.controller;

import com.prueba.dto.EntrenadorRequest;
import com.prueba.dto.EntrenadorResponse;
import com.prueba.service.EntrenadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @PostMapping
    public ResponseEntity<EntrenadorResponse> crear(@Valid @RequestBody EntrenadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entrenadorService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<EntrenadorResponse>> obtenerEntrenadores() {
        return ResponseEntity.ok(entrenadorService.obtenerEntrenadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrenadorResponse> obtenerEntrenador(@PathVariable Long id) {
        return ResponseEntity.ok(entrenadorService.obtenerEntrenador(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrenadorResponse> modificar(@PathVariable Long id, @Valid @RequestBody EntrenadorRequest request) {
        return ResponseEntity.ok(entrenadorService.modificar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        entrenadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
