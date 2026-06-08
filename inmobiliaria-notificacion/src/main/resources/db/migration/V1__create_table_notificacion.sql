CREATE TABLE notificacion (
idNotificacion  BIGINT NOT NULL AUTO_INCREMENT,
idCliente       BIGINT NOT NULL,
tipo            VARCHAR(100) NOT NULL,
mensaje         VARCHAR(500) NOT NULL,
fechaEnvio      DATETIME NOT NULL,
leida           BOOLEAN NOT NULL DEFAULT FALSE,
referencia      VARCHAR(100),
PRIMARY KEY (idNotificacion)
);
