CREATE DATABASE IF NOT EXISTS membresia_db;
USE membresia_db;

CREATE TABLE IF NOT EXISTS tipo_membresia (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(100) NOT NULL UNIQUE,
    descripcion   VARCHAR(255) NOT NULL,
    precio        DOUBLE       NOT NULL,
    duracion_dias INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS membresia (
    id                BIGINT  NOT NULL AUTO_INCREMENT,
    cliente_id        BIGINT  NOT NULL UNIQUE,
    tipo_membresia_id BIGINT  NOT NULL,
    fecha_inicio      DATE    NOT NULL,
    fecha_fin         DATE    NOT NULL,
    activa            BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_membresia_tipo
        FOREIGN KEY (tipo_membresia_id) REFERENCES tipo_membresia(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
