package com.prueba.controller;

import com.prueba.dto.ReservaRequest;
import com.prueba.dto.ReservaResponse;
import com.prueba.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponse> crear(@Valid @RequestBody ReservaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> obtenerReservas() {
        return ResponseEntity.ok(reservaService.obtenerReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> obtenerReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerReserva(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> modificar(@PathVariable Long id, @Valid @RequestBody ReservaRequest request) {
        return ResponseEntity.ok(reservaService.modificar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
