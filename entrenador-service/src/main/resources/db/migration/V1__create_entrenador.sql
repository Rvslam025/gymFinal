CREATE TABLE IF NOT EXISTS entrenador (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    telefono     VARCHAR(30)  NOT NULL,
    activo       BOOLEAN      NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;