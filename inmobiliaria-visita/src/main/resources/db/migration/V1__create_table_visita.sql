CREATE TABLE visita (
idVisita        BIGINT NOT NULL AUTO_INCREMENT,
idCliente       BIGINT NOT NULL,
idPropiedad     BIGINT NOT NULL,
idAgente        BIGINT NOT NULL,
fechaVisita     DATE NOT NULL,
horaVisita      TIME NOT NULL,
estado          VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
observaciones   VARCHAR(500),
PRIMARY KEY (idVisita)
);