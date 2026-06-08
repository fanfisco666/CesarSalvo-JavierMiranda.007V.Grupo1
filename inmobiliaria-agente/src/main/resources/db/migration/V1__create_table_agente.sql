CREATE TABLE agente (
idAgente BIGINT NOT NULL AUTO_INCREMENT,
rutAgente VARCHAR(13) NOT NULL,
nombreAgente VARCHAR(100) NOT NULL,
apellidosAgente VARCHAR(150) NOT NULL,
correoAgente VARCHAR(150) NOT NULL,
PRIMARY KEY (idAgente),
CONSTRAINT uk_agente_rut UNIQUE (rutAgente)
);