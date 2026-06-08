CREATE TABLE pago (
idPago           BIGINT NOT NULL AUTO_INCREMENT,
idContrato       BIGINT NOT NULL,
idCliente        BIGINT NOT NULL,
montoPago        DECIMAL(15,2) NOT NULL,
fechaPago        DATE NOT NULL,
fechaVencimiento DATE NOT NULL,
metodoPago       VARCHAR(50) NOT NULL,
estado           VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',
numeroCuota      INTEGER,
observaciones    VARCHAR(500),
PRIMARY KEY (idPago)
);