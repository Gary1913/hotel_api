package com.hotel.hotel_api.excepcion;

public class CheckOutInvalidoException extends RuntimeException {
    public CheckOutInvalidoException(String mensaje) {
        super(mensaje);
    }
}