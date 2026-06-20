package com.prueba.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "membresia")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK al cliente — vive en cliente-service, se guarda solo el ID
    @Column(name = "cliente_id", nullable = false, unique = true)
    private Long clienteId;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Boolean activa;

    // Relación JPA real dentro del mismo microservicio
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_membresia_id", nullable = false)
    private TipoMembresia tipoMembresia;
}
