CREATE DATABASE IF NOT EXISTS db_reserva;
USE db_reserva;

CREATE TABLE IF NOT EXISTS reserva (
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    fecha_reserva DATE        NOT NULL,
    estado        VARCHAR(50) NOT NULL,  -- CONFIRMADA, CANCELADA, PENDIENTE
    cliente_id    BIGINT      NOT NULL,  -- FK suelta al microservicio de Cliente
    clase_id      BIGINT      NOT NULL,  -- FK suelta al microservicio de Clase
    PRIMARY KEY (id)
);