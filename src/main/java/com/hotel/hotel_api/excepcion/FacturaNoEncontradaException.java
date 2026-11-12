package com.hotel.hotel_api.excepcion;

public class FacturaNoEncontradaException extends RuntimeException {
    public FacturaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}