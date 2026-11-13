-- =====================================================
-- Script 3: Datos de prueba
-- Incluye hospedajes finalizados (con factura) y activos
-- para cada tipo de habitación: ESTANDAR, PREMIER y SUITE.
-- Ejecutar después de los scripts 1 y 2, sobre una base limpia.
-- =====================================================

USE hotel_db;

-- -----------------------------------------------------
-- Clientes
-- -----------------------------------------------------
INSERT INTO cliente (id, identificacion, nombre_completo, correo_electronico, telefono, fecha_creacion) VALUES
(1, '1001', 'Ana María Gómez',    'ana.gomez@correo.com',     '3001112233', '2026-10-01 09:00:00'),
(2, '1002', 'Carlos Pérez Ruiz',  'carlos.perez@correo.com',  '3002223344', '2026-10-01 09:30:00'),
(3, '1003', 'Laura Torres Díaz',  'laura.torres@correo.com',  '3003334455', '2026-10-01 10:00:00'),
(4, '1004', 'Jorge Ramírez León', 'jorge.ramirez@correo.com', '3004445566', '2026-11-01 08:00:00'),
(5, '1005', 'Sofía Herrera Mora', 'sofia.herrera@correo.com', '3005556677', '2026-11-01 08:30:00'),
(6, '1006', 'Andrés Castro Vega', 'andres.castro@correo.com', '3006667788', '2026-11-01 09:00:00'),
(7, '1007', 'Valentina Ríos Paz', 'valentina.rios@correo.com','3007778899', '2026-11-01 09:30:00');
-- El cliente 7 no tiene hospedajes: sirve para probar un Check-In nuevo.

-- -----------------------------------------------------
-- Hospedajes FINALIZADOS (activo = 0) con su factura
-- Reproducen los ejemplos de cálculo de noches de la guía.
-- -----------------------------------------------------
INSERT INTO hospedaje (id, cliente_id, habitacion_id, fecha_hora_ingreso, fecha_hora_salida, activo, fecha_creacion) VALUES
-- Ejemplo 1 (ESTANDAR): ingreso 10/10 4:00 PM, salida 11/10 12:30 PM -> 1 noche
(1, 1, (SELECT id FROM habitacion WHERE numero_habitacion = 101),
    '2026-10-10 16:00:00', '2026-10-11 12:30:00', 0, '2026-10-10 16:00:00'),
-- Ejemplo 2 (PREMIER): ingreso 10/10 4:00 PM, salida 11/10 1:01 PM -> 2 noches
(2, 2, (SELECT id FROM habitacion WHERE numero_habitacion = 201),
    '2026-10-10 16:00:00', '2026-10-11 13:01:00', 0, '2026-10-10 16:00:00'),
-- Ejemplo 3 (SUITE): ingreso 10/10 4:00 PM, salida 10/10 8:00 PM -> 1 noche (mínimo)
(3, 3, (SELECT id FROM habitacion WHERE numero_habitacion = 301),
    '2026-10-10 16:00:00', '2026-10-10 20:00:00', 0, '2026-10-10 16:00:00'),
-- Historial: la cliente 1 tiene una segunda estadía (ESTANDAR, 3 noches)
(4, 1, (SELECT id FROM habitacion WHERE numero_habitacion = 103),
    '2026-10-20 16:00:00', '2026-10-23 11:00:00', 0, '2026-10-20 16:00:00');

INSERT INTO factura (id, hospedaje_id, fecha_generacion, cantidad_noches, valor_noche, valor_total) VALUES
(1, 1, '2026-10-11 12:30:00', 1,  300000,  300000),   -- 1 x   300.000
(2, 2, '2026-10-11 13:01:00', 2,  500000, 1000000),   -- 2 x   500.000
(3, 3, '2026-10-10 20:00:00', 1, 1000000, 1000000),   -- 1 x 1.000.000
(4, 4, '2026-10-23 11:00:00', 3,  300000,  900000);   -- 3 x   300.000

-- -----------------------------------------------------
-- Hospedajes ACTIVOS (activo = 1): uno por cada tipo
-- Las fechas son relativas a HOY, para que al hacer Check-Out
-- por la API siempre se facturen varias noches.
-- -----------------------------------------------------
INSERT INTO hospedaje (id, cliente_id, habitacion_id, fecha_hora_ingreso, fecha_hora_salida, activo, fecha_creacion) VALUES
-- ESTANDAR: ingresó hace 2 días a las 4:00 PM
(5, 4, (SELECT id FROM habitacion WHERE numero_habitacion = 102),
    TIMESTAMP(DATE_SUB(CURDATE(), INTERVAL 2 DAY), '16:00:00'), NULL, 1,
    TIMESTAMP(DATE_SUB(CURDATE(), INTERVAL 2 DAY), '16:00:00')),
-- PREMIER: ingresó hace 1 día a las 4:00 PM
(6, 5, (SELECT id FROM habitacion WHERE numero_habitacion = 202),
    TIMESTAMP(DATE_SUB(CURDATE(), INTERVAL 1 DAY), '16:00:00'), NULL, 1,
    TIMESTAMP(DATE_SUB(CURDATE(), INTERVAL 1 DAY), '16:00:00')),
-- SUITE: ingresó hace 3 días a las 4:00 PM
(7, 6, (SELECT id FROM habitacion WHERE numero_habitacion = 302),
    TIMESTAMP(DATE_SUB(CURDATE(), INTERVAL 3 DAY), '16:00:00'), NULL, 1,
    TIMESTAMP(DATE_SUB(CURDATE(), INTERVAL 3 DAY), '16:00:00'));

-- Las habitaciones con hospedaje activo quedan OCUPADAS
UPDATE habitacion SET estado = 'OCUPADA' WHERE numero_habitacion IN (102, 202, 302);