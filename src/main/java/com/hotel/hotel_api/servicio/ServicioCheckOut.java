package com.hotel.hotel_api.servicio;

import com.hotel.hotel_api.dto.CheckOutRequest;
import com.hotel.hotel_api.dto.FacturaResponse;

public interface ServicioCheckOut {

    /**
     * Registra la salida del cliente, libera la habitación,
     * inactiva el hospedaje y genera la factura.
     *
     * @throws com.hotel.hotel_api.excepcion.ClienteNoEncontradoException          si el cliente no existe
     * @throws com.hotel.hotel_api.excepcion.HospedajeActivoNoEncontradoException si el cliente no está hospedado
     * @throws com.hotel.hotel_api.excepcion.CheckOutInvalidoException            si los datos del hospedaje son inconsistentes
     */
    FacturaResponse realizarCheckOut(CheckOutRequest request);
}