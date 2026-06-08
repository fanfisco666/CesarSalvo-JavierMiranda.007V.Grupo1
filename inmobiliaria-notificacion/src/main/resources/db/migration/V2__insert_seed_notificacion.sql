INSERT INTO notificacion (idCliente, tipo, mensaje, fechaEnvio, leida, referencia)
VALUES (1, 'VISITA_AGENDADA', 'Su visita ha sido agendada para el 20/05/2026', '2026-05-15 10:00:00', FALSE, 'VISITA-1');

INSERT INTO notificacion (idCliente, tipo, mensaje, fechaEnvio, leida, referencia)
VALUES (2, 'CONTRATO_CREADO', 'Su contrato de arriendo ha sido creado exitosamente', '2026-05-15 11:00:00', TRUE, 'CONTRATO-1');

INSERT INTO notificacion (idCliente, tipo, mensaje, fechaEnvio, leida, referencia)
VALUES (1, 'PAGO_PENDIENTE', 'Tiene un pago pendiente con vencimiento el 05/06/2026', '2026-05-15 12:00:00', FALSE, 'PAGO-1');
