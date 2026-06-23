CREATE TABLE IF NOT EXISTS categoria_producto (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS producto (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(150) NOT NULL,
    descripcion  VARCHAR(500) NOT NULL,
    precio       DOUBLE       NOT NULL,
    stock        INT          NOT NULL DEFAULT 0,
    disponible   TINYINT(1)   NOT NULL DEFAULT 1,
    categoria_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_id) REFERENCES categoria_producto(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;