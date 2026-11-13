package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.excepcion.CheckOutInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraNochesTest {

    @Test
    @DisplayName("Ejemplo 1: sale antes de la 1:00 PM del día siguiente -> 1 noche")
    void ejemplo1() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida  = LocalDateTime.of(2026, 10, 11, 12, 30);
        assertEquals(1, CalculadoraNoches.calcularNoches(ingreso, salida));
    }

    @Test
    @DisplayName("Ejemplo 2: sale a la 1:01 PM del día siguiente -> 2 noches")
    void ejemplo2() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida  = LocalDateTime.of(2026, 10, 11, 13, 1);
        assertEquals(2, CalculadoraNoches.calcularNoches(ingreso, salida));
    }

    @Test
    @DisplayName("Ejemplo 3: sale el mismo día -> mínimo 1 noche")
    void ejemplo3() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida  = LocalDateTime.of(2026, 10, 10, 20, 0);
        assertEquals(1, CalculadoraNoches.calcularNoches(ingreso, salida));
    }

    @Test
    @DisplayName("Salida a la 1:00 PM exacta no cobra noche adicional")
    void salidaExactaALaUna() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida  = LocalDateTime.of(2026, 10, 11, 13, 0);
        assertEquals(1, CalculadoraNoches.calcularNoches(ingreso, salida));
    }

    @Test
    @DisplayName("Estadía de varios días con salida tardía")
    void variosDiasConSalidaTardia() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida  = LocalDateTime.of(2026, 10, 13, 15, 0);
        assertEquals(4, CalculadoraNoches.calcularNoches(ingreso, salida));
    }

    @Test
    @DisplayName("Salida anterior al ingreso lanza CheckOutInvalidoException")
    void salidaAnteriorAlIngreso() {
        LocalDateTime ingreso = LocalDateTime.of(2026, 10, 10, 16, 0);
        LocalDateTime salida  = LocalDateTime.of(2026, 10, 9, 10, 0);
        assertThrows(CheckOutInvalidoException.class,
                () -> CalculadoraNoches.calcularNoches(ingreso, salida));
    }
}