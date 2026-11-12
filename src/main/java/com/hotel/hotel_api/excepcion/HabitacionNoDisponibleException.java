package com.hotel.hotel_api.excepcion;

public class HabitacionNoDisponibleException extends RuntimeException {
    public HabitacionNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}