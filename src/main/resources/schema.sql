-- Tabla de autores
CREATE TABLE autor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    anio_nacimiento INT,
    anio_muerte INT
);

-- Tabla de libros
CREATE TABLE libro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo TEXT,
    idioma VARCHAR(255),
    numero_descargas INT,
    autor_id BIGINT,
    CONSTRAINT fk_autor FOREIGN KEY (autor_id) REFERENCES autor(id)
);