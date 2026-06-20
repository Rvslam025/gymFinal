CREATE DATABASE IF NOT EXISTS db_pago;
USE db_pago;

CREATE TABLE IF NOT EXISTS pago (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    monto       DOUBLE      NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    estado      VARCHAR(50) NOT NULL,
    fecha_pago  DATETIME    NOT NULL,
    venta_id    BIGINT      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_pago_venta UNIQUE (venta_id)
);
