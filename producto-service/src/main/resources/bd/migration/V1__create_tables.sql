CREATE TABLE IF NOT EXISTS categoria_producto (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_categoria_nombre UNIQUE (nombre)
);

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
);

INSERT INTO categoria_producto (nombre, descripcion) VALUES
    ('Suplementos',  'Proteinas, creatinas y vitaminas'),
    ('Equipamiento', 'Guantes, cinturones y accesorios de entrenamiento'),
    ('Indumentaria', 'Ropa y calzado deportivo');

INSERT INTO producto (nombre, descripcion, precio, stock, disponible, categoria_id) VALUES
    ('Proteina Whey 1kg', 'Proteina de suero de leche sabor vainilla',  25990, 50, 1, 1),
    ('Guantes de boxeo',  'Guantes profesionales 14oz cuero sintetico', 18990, 20, 1, 2),
    ('Camiseta Dry-Fit',  'Camiseta deportiva tela transpirable',         9990, 35, 1, 3),
    ('Creatina 300g',     'Creatina monohidratada sin sabor',            15990, 40, 1, 1);
