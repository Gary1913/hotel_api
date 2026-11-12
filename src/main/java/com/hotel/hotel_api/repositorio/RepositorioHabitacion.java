package com.hotel.hotel_api.repositorio;

import com.hotel.hotel_api.entidad.EstadoHabitacion;
import com.hotel.hotel_api.entidad.Habitacion;
import com.hotel.hotel_api.entidad.TipoHabitacion;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioHabitacion extends JpaRepository<Habitacion, Long> {


    Optional<Habitacion> findByNumeroHabitacion(Integer numeroHabitacion);


    List<Habitacion> findByTipoHabitacionAndEstadoOrderByNumeroHabitacionAsc(
            TipoHabitacion tipoHabitacion, EstadoHabitacion estado);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Habitacion> findFirstByTipoHabitacionAndEstadoOrderByNumeroHabitacionAsc(
            TipoHabitacion tipoHabitacion, EstadoHabitacion estado);
}