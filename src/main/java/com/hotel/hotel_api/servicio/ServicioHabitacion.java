package com.hotel.hotel_api.servicio;

import com.hotel.hotel_api.dto.HabitacionResponse;
import com.hotel.hotel_api.entidad.TipoHabitacion;

import java.util.List;

public interface ServicioHabitacion {

    List<HabitacionResponse> consultarTodas();

    /**
     * @throws com.hotel.hotel_api.excepcion.HabitacionNoEncontradaException si no existe
     */
    HabitacionResponse consultarPorNumero(Integer numeroHabitacion);

    /**
     * Lista las habitaciones DISPONIBLES del tipo indicado.
     * Si no hay ninguna, devuelve una lista vacía.
     */
    List<HabitacionResponse> buscarDisponiblePorTipo(TipoHabitacion tipoHabitacion);
}