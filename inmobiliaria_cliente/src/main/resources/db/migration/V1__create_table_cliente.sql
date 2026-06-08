CREATE TABLE cliente (
idCliente BIGINT NOT NULL AUTO_INCREMENT,
rutCliente VARCHAR(13) NOT NULL,
nombreCliente VARCHAR(100) NOT NULL,
apellidosCliente VARCHAR(150) NOT NULL,
correoCliente VARCHAR(150) NOT NULL,
PRIMARY KEY (idCliente),
CONSTRAINT uk_cliente_rut UNIQUE (rutCliente)
);