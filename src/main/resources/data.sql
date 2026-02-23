-- Datos de autores de prueba
INSERT INTO autor (nombre, anio_nacimiento, anio_muerte) VALUES
('Gabriel Garcia Marquez', 1927, 2014),
('Isabel Allende', 1942, NULL);

-- Datos de libros de prueba
INSERT INTO libro (titulo, idioma, numero_descargas, autor_id) VALUES
('Cien Años de Soledad', 'Español', 5000, 1),
('La Casa de los Espíritus', 'Español', 3000, 2);