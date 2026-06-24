package com.prueba.repository;

import com.prueba.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Long> {

}
