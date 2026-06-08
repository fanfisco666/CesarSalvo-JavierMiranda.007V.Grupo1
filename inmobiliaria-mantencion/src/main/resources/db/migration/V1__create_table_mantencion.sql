CREATE TABLE mantencion (
idMantencion    BIGINT AUTO_INCREMENT PRIMARY KEY,
descripcion    TEXT,
estado         VARCHAR(50) NOT NULL,
fechaInicio   DATE NOT NULL,
fechaFin      DATE,
idAgente        BIGINT NOT NULL,
idPropiedad   BIGINT NOT NULL
);