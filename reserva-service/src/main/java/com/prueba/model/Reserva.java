package com.prueba.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "reserva")
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_reserva", nullable = false)
    private Date fechaReserva;

    @Column(nullable = false)
    private String estado;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "clase_id", nullable = false)
    private Long claseId;
}
