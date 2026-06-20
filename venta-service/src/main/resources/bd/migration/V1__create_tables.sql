CREATE DATABASE IF NOT EXISTS db_venta;
USE db_venta;

CREATE TABLE IF NOT EXISTS venta (
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    fecha_venta DATETIME NOT NULL,
    cantidad    INT      NOT NULL,
    total       DOUBLE   NOT NULL,
    producto_id BIGINT   NOT NULL,
    cliente_id  BIGINT   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS detalle_venta (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    producto_id     BIGINT NOT NULL,
    cantidad        INT    NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    subtotal        DOUBLE NOT NULL,
    venta_id        BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (venta_id) REFERENCES venta(id)
        ON DELETE CASCADE
);
