CREATE DATABASE IF NOT EXISTS db_tipo_membresia;
USE db_tipo_membresia;

CREATE TABLE IF NOT EXISTS tipo_membresia (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(100) NOT NULL UNIQUE,
    descripcion   VARCHAR(255) NOT NULL,
    precio        DOUBLE       NOT NULL,
    duracion_dias INT          NOT NULL,
    PRIMARY KEY (id)
);