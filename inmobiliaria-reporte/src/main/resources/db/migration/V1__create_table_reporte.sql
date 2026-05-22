CREATE TABLE REPORTE (
    idReporte BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    tipoReporte VARCHAR(255) NOT NULL,
    idPropiedad BIGINT NOT NULL,
    idUsuario BIGINT NOT NULL,
    idAgente BIGINT NOT NULL,

    PRIMARY KEY (idReporte)
);