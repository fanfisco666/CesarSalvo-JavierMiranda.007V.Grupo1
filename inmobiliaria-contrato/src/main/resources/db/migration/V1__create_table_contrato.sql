CREATE TABLE CONTRATO (
idContrato      BIGINT NOT NULL AUTO_INCREMENT,
idCliente       BIGINT NOT NULL,
idPropiedad     BIGINT NOT NULL,
idAgente        BIGINT NOT NULL,
idVisita        BIGINT NOT NULL,
tipoContrato    VARCHAR(50) NOT NULL,
montoTotal      DECIMAL(15,2) NOT NULL,
fechaInicio     DATE NOT NULL,
fechaFin        DATE,
estado          VARCHAR(50) NOT NULL DEFAULT 'VIGENTE',
observaciones   VARCHAR(500),
PRIMARY KEY (idContrato)
);