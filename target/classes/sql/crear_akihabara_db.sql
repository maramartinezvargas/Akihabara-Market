-- Creación de la BD akihabara_db (si no existe la borra)
drop database if exists akihabara_db;
CREATE DATABASE akihabara_db;
USE akihabara_db;

DROP TABLE IF EXISTS productos;
CREATE TABLE productos (
    id INT PRIMARY KEY auto_increment,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(100),
    precio DECIMAL(10,2),
    stock INT
);

ALTER TABLE productos AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	telefono VARCHAR(20),
	fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

ALTER TABLE clientes AUTO_INCREMENT = 1;

-- Creación de un usuario con contraseña con permisos de CRUD para el manejo de la BD
CREATE USER 'kenji'@'localhost' IDENTIFIED BY 'akihabara';
GRANT SELECT, INSERT, UPDATE, DELETE ON akihabara_db.* TO 'kenji'@'localhost';
FLUSH PRIVILEGES;

-- INSERTS DE PRUEBA DE PRODUCTOS
INSERT INTO productos (nombre, categoria, precio, stock) VALUES 
('Figura de Anya Forger', 'Figura', 59.95, 8),
('Manga Chainsaw Man Vol.1', 'Manga', 9.99, 20),
('Póster Studio Ghibli Colección', 'Póster', 15.59, 15);

-- INSERTS DE PRUEBA DE PRODUCTOS
INSERT INTO clientes (nombre, email, telefono, fecha_registro) VALUES 
('Mara', 'mara@gmail.com', '647807965', CURDATE()),
('Juan', 'juan@gmail.com', '610252635', CURDATE()),
('Pepe', 'pepe@gmail.com', '678495623', CURDATE());