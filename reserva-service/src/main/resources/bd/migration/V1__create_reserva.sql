CREATE TABLE IF NOT EXISTS reserva (
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    fecha_reserva DATE        NOT NULL,
    estado        VARCHAR(50) NOT NULL,
    cliente_id    BIGINT      NOT NULL,
    clase_id      BIGINT      NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;