package com.prueba.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "asistencia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "registro_entrada")
    private LocalDateTime registroEntrada;

    @Column(name = "registro_salida")
    private LocalDateTime registroSalida;

    // FK hacia cliente en otro microservicio — se guarda solo el ID
    @Column(name = "cliente_id")
    private Long clienteId;
}
