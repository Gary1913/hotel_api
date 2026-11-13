package com.hotel.hotel_api.servicio;

import com.hotel.hotel_api.dto.ClienteRequest;
import com.hotel.hotel_api.dto.ClienteResponse;

import java.util.List;

public interface ServicioCliente {

    /**
     * Registra un nuevo cliente.
     * @throws com.hotel.hotel_api.excepcion.ClienteExisteException si la identificación ya está registrada
     */
    ClienteResponse crearCliente(ClienteRequest request);

    /**
     * @throws com.hotel.hotel_api.excepcion.ClienteNoEncontradoException si no existe
     */
    ClienteResponse consultarPorId(Long id);

    /**
     * @throws com.hotel.hotel_api.excepcion.ClienteNoEncontradoException si no existe
     */
    ClienteResponse consultarPorIdentificacion(String identificacion);

    List<ClienteResponse> consultarTodos();
}