package com.hotel.hotel_api.excepcion;

public class HospedajeActivoNoEncontradoException extends RuntimeException {
    public HospedajeActivoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}