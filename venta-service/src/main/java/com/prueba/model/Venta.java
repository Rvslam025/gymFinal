package com.prueba.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "venta")
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta", nullable = false)
    private Date fechaVenta;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private double total;

    // FK al producto y cliente — viven en otros microservicios
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    // Relación JPA real dentro del mismo microservicio
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();
}
