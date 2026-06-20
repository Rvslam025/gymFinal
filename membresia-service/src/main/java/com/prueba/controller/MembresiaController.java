package com.prueba.controller;

import com.prueba.dto.MembresiaRequest;
import com.prueba.dto.MembresiaResponse;
import com.prueba.service.MembresiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membresias")
public class MembresiaController {

    @Autowired
    private MembresiaService membresiaService;

    @PostMapping
    public ResponseEntity<MembresiaResponse> crear(@Valid @RequestBody MembresiaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membresiaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<MembresiaResponse>> listar() {
        return ResponseEntity.ok(membresiaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembresiaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(membresiaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembresiaResponse> modificar(
            @PathVariable Long id,
            @Valid @RequestBody MembresiaRequest request) {
        return ResponseEntity.ok(membresiaService.modificar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        membresiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activa/{clienteId}")
    public ResponseEntity<Boolean> validarActiva(@PathVariable Long clienteId) {
        return ResponseEntity.ok(membresiaService.validarActiva(clienteId));
    }
}
