CREATE TABLE IF NOT EXISTS asistencia (
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(100) NOT NULL,
    registro_entrada DATETIME     NULL,
    registro_salida  DATETIME     NULL,
    cliente_id       BIGINT       NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;