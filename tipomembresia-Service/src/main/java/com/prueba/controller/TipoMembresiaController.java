package com.prueba.controller;

import com.prueba.dto.TipoMembresiaRequest;
import com.prueba.dto.TipoMembresiaResponse;
import com.prueba.service.TipoMembresiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-membresias")
public class TipoMembresiaController {

    @Autowired
    private TipoMembresiaService tipoMembresiaService;

    @PostMapping
    public ResponseEntity<TipoMembresiaResponse> crear(@Valid @RequestBody TipoMembresiaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoMembresiaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<TipoMembresiaResponse>> obtenerTipoMembresias() {
        return ResponseEntity.ok(tipoMembresiaService.obtenerTipoMembresias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMembresiaResponse> obtenerTipoMembresia(@PathVariable Long id) {
        return ResponseEntity.ok(tipoMembresiaService.obtenerTipoMembresia(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMembresiaResponse> modificar(@PathVariable Long id, @Valid @RequestBody TipoMembresiaRequest request) {
        return ResponseEntity.ok(tipoMembresiaService.modificar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipoMembresiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
