package com.hotel.hotel_api.excepcion;

public class ClienteExisteException extends RuntimeException {
    public ClienteExisteException(String mensaje) {
        super(mensaje);
    }
}