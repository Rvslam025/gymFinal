CREATE DATABASE IF NOT EXISTS db_asistencia;
USE db_asistencia;

CREATE TABLE IF NOT EXISTS asistencia (
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(100) NOT NULL,  -- ← faltaba esta columna
    registro_entrada DATETIME,
    registro_salida  DATETIME,
    cliente_id       BIGINT,  -- FK suelta, opcional
    PRIMARY KEY (id)
);