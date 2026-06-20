package com.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.model.Asistencia;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia,Long> {


}
