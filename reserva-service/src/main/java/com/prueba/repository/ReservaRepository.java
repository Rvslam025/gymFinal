package com.prueba.repository;

import com.prueba.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    // ← NUEVO: verifica si ya existe reserva del mismo cliente para la misma clase
    boolean existsByClienteIdAndClaseId(Long clienteId, Long claseId);
}
