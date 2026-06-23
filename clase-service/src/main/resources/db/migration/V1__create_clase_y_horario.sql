CREATE TABLE IF NOT EXISTS clase (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nombre        VARCHAR(100) NOT NULL UNIQUE,
    instructor    VARCHAR(100) NOT NULL,
    capacidad     INT          NOT NULL,
    entrenador_id BIGINT       NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS horario (
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    dia_semana  VARCHAR(20) NOT NULL,
    hora_inicio TIME        NOT NULL,
    hora_fin    TIME        NOT NULL,
    clase_id    BIGINT      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_horario_clase
        FOREIGN KEY (clase_id) REFERENCES clase(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;