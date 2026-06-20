package com.prueba.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private Integer capacidad;

    // FK al entrenador — vive en entrenador-service
    @Column(name = "entrenador_id")
    private Long entrenadorId;

    // Relación JPA real dentro del mismo microservicio
    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Horario> horarios = new ArrayList<>();

}
