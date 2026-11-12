package com.hotel.hotel_api.excepcion;

public class HabitacionNoEncontradaException extends RuntimeException {
    public HabitacionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}