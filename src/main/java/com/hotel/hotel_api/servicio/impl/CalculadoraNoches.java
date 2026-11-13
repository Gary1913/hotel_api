package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.configuracion.HorariosHotel;
import com.hotel.hotel_api.excepcion.CheckOutInvalidoException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public final class CalculadoraNoches {

    private CalculadoraNoches() {
    }

    /**
     * Calcula las noches a facturar según las reglas del hotel:
     * - Noches = días de calendario entre la fecha de ingreso y la de salida.
     * - Si la salida es posterior a la hora oficial de Check-Out (01:00 PM), se suma una noche.
     * - Mínimo se factura una (1) noche.
     */
    public static int calcularNoches(LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida) {
        if (fechaHoraSalida.isBefore(fechaHoraIngreso)) {
            throw new CheckOutInvalidoException(
                    "La fecha de salida no puede ser anterior a la fecha de ingreso");
        }

        // 1. Días de calendario entre las dos fechas (sin importar la hora)
        long noches = ChronoUnit.DAYS.between(
                fechaHoraIngreso.toLocalDate(),
                fechaHoraSalida.toLocalDate());

        // 2. Salida tardía: después de la 1:00 PM se cobra una noche adicional
        LocalTime horaSalida = fechaHoraSalida.toLocalTime().truncatedTo(ChronoUnit.MINUTES);
        if (horaSalida.isAfter(HorariosHotel.HORA_CHECK_OUT)) {
            noches++;
        }

        // 3. Mínimo una noche
        return (int) Math.max(1, noches);
    }
}