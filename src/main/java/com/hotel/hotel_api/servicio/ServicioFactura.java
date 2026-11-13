package com.hotel.hotel_api.servicio;

import com.hotel.hotel_api.dto.FacturaResponse;

import java.util.List;

public interface ServicioFactura {

    /**
     * @throws com.hotel.hotel_api.excepcion.FacturaNoEncontradaException si no existe
     */
    FacturaResponse consultarPorId(Long id);

    List<FacturaResponse> consultarTodas();
}