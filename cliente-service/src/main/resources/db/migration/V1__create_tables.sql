CREATE DATABASE IF NOT EXISTS cliente_bd;
USE cliente_db;

CREATE TABLE IF NOT EXISTS cliente (
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(100) NOT NULL,
    apellido         VARCHAR(100) NOT NULL,
    email            VARCHAR(150) NOT NULL UNIQUE,
    telefono         VARCHAR(30)  NOT NULL,
    fecha_nacimiento DATE         NOT NULL,
    activo           BOOLEAN      NOT NULL,
    PRIMARY KEY (id)
);