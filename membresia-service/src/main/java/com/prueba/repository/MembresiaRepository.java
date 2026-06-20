package com.prueba.repository;

import com.prueba.model.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Long> {
    Optional<Membresia> findByClienteId(Long clienteId);
    boolean existsByClienteId(Long clienteId);
}
