INSERT INTO pago (idContrato, idCliente, montoPago, fechaPago, fechaVencimiento, metodoPago, estado, numeroCuota, observaciones)
VALUES (1, 1, 450000.00, '2026-06-01', '2026-06-05', 'TRANSFERENCIA', 'PAGADO', 1, 'Primer mes arriendo');

INSERT INTO pago (idContrato, idCliente, montoPago, fechaPago, fechaVencimiento, metodoPago, estado, numeroCuota, observaciones)
VALUES (1, 1, 450000.00, '2026-07-01', '2026-07-05', 'TRANSFERENCIA', 'PENDIENTE', 2, 'Segundo mes arriendo');

INSERT INTO pago (idContrato, idCliente, montoPago, fechaPago, fechaVencimiento, metodoPago, estado, numeroCuota, observaciones)
VALUES (2, 2, 153000000.00, '2026-06-15', '2026-06-15', 'CHEQUE', 'PAGADO', 1, 'Pago total venta');
