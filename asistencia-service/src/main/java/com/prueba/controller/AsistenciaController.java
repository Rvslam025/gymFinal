package com.prueba.controller;

import com.prueba.DTO.AsistenciaRequest;
import com.prueba.DTO.AsistenciaResponse;
import com.prueba.service.AsistenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/entrada")
    public ResponseEntity<AsistenciaResponse> registrarEntrada(
            @Valid @RequestBody AsistenciaRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(asistenciaService.registrarEntrada(request));
    }

    @PutMapping("/salida/{id}")
    public ResponseEntity<AsistenciaResponse> registrarSalida(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(asistenciaService.registrarSalida(id));
    }

    @GetMapping
    public ResponseEntity<List<AsistenciaResponse>> listar() {
        return ResponseEntity.ok(asistenciaService.listarAsistencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(asistenciaService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        asistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
