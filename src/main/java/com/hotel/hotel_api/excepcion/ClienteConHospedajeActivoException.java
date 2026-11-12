package com.hotel.hotel_api.excepcion;

public class ClienteConHospedajeActivoException extends RuntimeException {
    public ClienteConHospedajeActivoException(String mensaje) {
        super(mensaje);
    }
}