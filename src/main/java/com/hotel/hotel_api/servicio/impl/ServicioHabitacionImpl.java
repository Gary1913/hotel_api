package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.dto.HabitacionResponse;
import com.hotel.hotel_api.entidad.EstadoHabitacion;
import com.hotel.hotel_api.entidad.Habitacion;
import com.hotel.hotel_api.entidad.TipoHabitacion;
import com.hotel.hotel_api.excepcion.HabitacionNoEncontradaException;
import com.hotel.hotel_api.repositorio.RepositorioHabitacion;
import com.hotel.hotel_api.servicio.ServicioHabitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioHabitacionImpl implements ServicioHabitacion {

    private final RepositorioHabitacion repositorioHabitacion;

    @Override
    public List<HabitacionResponse> consultarTodas() {
        return repositorioHabitacion.findAll(Sort.by("numeroHabitacion")).stream()
                .map(Mapeador::aHabitacionResponse)
                .toList();
    }

    @Override
    public HabitacionResponse consultarPorNumero(Integer numeroHabitacion) {
        Habitacion habitacion = repositorioHabitacion.findByNumeroHabitacion(numeroHabitacion)
                .orElseThrow(() -> new HabitacionNoEncontradaException(
                        "No existe la habitación número " + numeroHabitacion));
        return Mapeador.aHabitacionResponse(habitacion);
    }

    @Override
    public List<HabitacionResponse> buscarDisponiblePorTipo(TipoHabitacion tipoHabitacion) {
        return repositorioHabitacion
                .findByTipoHabitacionAndEstadoOrderByNumeroHabitacionAsc(
                        tipoHabitacion, EstadoHabitacion.DISPONIBLE)
                .stream()
                .map(Mapeador::aHabitacionResponse)
                .toList();
    }
}