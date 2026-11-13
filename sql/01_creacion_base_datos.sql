-- =====================================================
-- Script 1: Creación de la base de datos y tablas
-- Proyecto: API Hotel - Check-In y Check-Out
-- =====================================================

CREATE DATABASE IF NOT EXISTS hotel_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hotel_db;

-- -----------------------------------------------------
-- Tabla: cliente
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente (
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    identificacion     VARCHAR(20)  NOT NULL,
    nombre_completo    VARCHAR(150) NOT NULL,
    correo_electronico VARCHAR(150) NOT NULL,
    telefono           VARCHAR(20)  NOT NULL,
    fecha_creacion     DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_cliente_identificacion UNIQUE (identificacion)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabla: habitacion
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS habitacion (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    numero_habitacion INT           NOT NULL,
    piso              INT           NOT NULL,
    tipo_habitacion   ENUM('ESTANDAR', 'PREMIER', 'SUITE') NOT NULL,
    valor_noche       DECIMAL(12,2) NOT NULL,
    estado            ENUM('DISPONIBLE', 'OCUPADA')        NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_habitacion_numero UNIQUE (numero_habitacion)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabla: hospedaje
-- Relaciones: Cliente 1 --- N Hospedaje
--             Habitacion 1 --- N Hospedaje
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS hospedaje (
    id                 BIGINT      NOT NULL AUTO_INCREMENT,
    cliente_id         BIGINT      NOT NULL,
    habitacion_id      BIGINT      NOT NULL,
    fecha_hora_ingreso DATETIME(6) NOT NULL,
    fecha_hora_salida  DATETIME(6) NULL,
    activo             BIT(1)      NOT NULL,
    fecha_creacion     DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_hospedaje_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente (id),
    CONSTRAINT fk_hospedaje_habitacion
        FOREIGN KEY (habitacion_id) REFERENCES habitacion (id)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabla: factura (inmutable)
-- Relación: Hospedaje 1 --- 1 Factura
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS factura (
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    hospedaje_id     BIGINT        NOT NULL,
    fecha_generacion DATETIME(6)   NOT NULL,
    cantidad_noches  INT           NOT NULL,
    valor_noche      DECIMAL(12,2) NOT NULL,
    valor_total      DECIMAL(14,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_factura_hospedaje UNIQUE (hospedaje_id),
    CONSTRAINT fk_factura_hospedaje
        FOREIGN KEY (hospedaje_id) REFERENCES hospedaje (id)
) ENGINE = InnoDB;