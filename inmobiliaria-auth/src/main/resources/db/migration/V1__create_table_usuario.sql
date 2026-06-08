CREATE TABLE usuario (
idUsuario  BIGINT NOT NULL AUTO_INCREMENT,
username   VARCHAR(100) NOT NULL,
password   VARCHAR(255) NOT NULL,
rol        VARCHAR(50) NOT NULL,
activo     BOOLEAN NOT NULL DEFAULT TRUE,
PRIMARY KEY (idUsuario),
CONSTRAINT uk_usuario_username UNIQUE (username)
);