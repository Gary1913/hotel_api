package com.hotel.hotel_api.servicio;

import com.hotel.hotel_api.dto.CheckInRequest;
import com.hotel.hotel_api.dto.CheckInResponse;

public interface ServicioCheckIn {

    /**
     * Registra el ingreso de un cliente y le asigna automáticamente
     * una habitación disponible del tipo solicitado.
     *
     * @throws com.hotel.hotel_api.excepcion.ClienteNoEncontradoException       si el cliente no existe
     * @throws com.hotel.hotel_api.excepcion.ClienteConHospedajeActivoException si ya tiene un hospedaje activo
     * @throws com.hotel.hotel_api.excepcion.HabitacionNoDisponibleException    si no hay habitaciones libres de ese tipo
     */
    CheckInResponse realizarCheckIn(CheckInRequest request);
}