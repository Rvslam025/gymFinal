CREATE TABLE IF NOT EXISTS membresia (
    id                 BIGINT  NOT NULL AUTO_INCREMENT,
    cliente_id         BIGINT  NOT NULL UNIQUE,
    tipo_membresia_id  BIGINT  NOT NULL,
    fecha_inicio       DATE    NOT NULL,
    fecha_fin          DATE    NOT NULL,
    activa             BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;