package com.hotel.hotel_api.repositorio;

import com.hotel.hotel_api.entidad.EstadoHabitacion;
import com.hotel.hotel_api.entidad.Habitacion;
import com.hotel.hotel_api.entidad.TipoHabitacion;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioHabitacion extends JpaRepository<Habitacion, Long> {


    Optional<Habitacion> findByNumeroHabitacion(Integer numeroHabitacion);


    List<Habitacion> findByTipoHabitacionAndEstadoOrderByNumeroHabitacionAsc(
            TipoHabitacion tipoHabitacion, EstadoHabitacion estado);


    // Check-In: toma la primera habitación disponible del tipo pedido y bloquea la fila
    @Query(value = """
            SELECT * FROM habitacion
            WHERE tipo_habitacion = :tipo
              AND estado = 'DISPONIBLE'
            ORDER BY numero_habitacion
            LIMIT 1
            FOR UPDATE
            """, nativeQuery = true)
    Optional<Habitacion> buscarDisponibleParaCheckIn(@Param("tipo") String tipo);
}