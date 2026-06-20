package com.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prueba.dto.PagoRequest;
import com.prueba.dto.PagoResponse;
import com.prueba.service.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponse> crear(@Valid @RequestBody PagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<PagoResponse>> obtenerPagos() {
        return ResponseEntity.ok(pagoService.obtenerPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> obtenerPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPago(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
