package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

}
