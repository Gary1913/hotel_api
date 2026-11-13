package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.dto.CheckInRequest;
import com.hotel.hotel_api.dto.CheckInResponse;
import com.hotel.hotel_api.entidad.Cliente;
import com.hotel.hotel_api.entidad.EstadoHabitacion;
import com.hotel.hotel_api.entidad.Habitacion;
import com.hotel.hotel_api.entidad.Hospedaje;
import com.hotel.hotel_api.excepcion.ClienteConHospedajeActivoException;
import com.hotel.hotel_api.excepcion.ClienteNoEncontradoException;
import com.hotel.hotel_api.excepcion.HabitacionNoDisponibleException;
import com.hotel.hotel_api.repositorio.RepositorioCliente;
import com.hotel.hotel_api.repositorio.RepositorioHabitacion;
import com.hotel.hotel_api.repositorio.RepositorioHospedaje;
import com.hotel.hotel_api.servicio.ServicioCheckIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServicioCheckInImpl implements ServicioCheckIn {

    private final RepositorioCliente repositorioCliente;
    private final RepositorioHabitacion repositorioHabitacion;
    private final RepositorioHospedaje repositorioHospedaje;

    @Override
    @Transactional
    public CheckInResponse realizarCheckIn(CheckInRequest request) {


        Cliente cliente = repositorioCliente.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNoEncontradoException(
                        "No existe un cliente con id " + request.getClienteId()));


        if (repositorioHospedaje.existsByClienteIdAndActivoTrue(cliente.getId())) {
            throw new ClienteConHospedajeActivoException(
                    "El cliente " + cliente.getNombreCompleto() + " ya tiene un hospedaje activo");
        }

        Habitacion habitacion = repositorioHabitacion
                .buscarDisponibleParaCheckIn(request.getTipoHabitacion().name())
                .orElseThrow(() -> new HabitacionNoDisponibleException(
                        "No existen habitaciones disponibles para el tipo " + request.getTipoHabitacion()));


        habitacion.setEstado(EstadoHabitacion.OCUPADA);


        Hospedaje hospedaje = Hospedaje.builder()
                .cliente(cliente)
                .habitacion(habitacion)
                .fechaHoraIngreso(LocalDateTime.now())
                .activo(true)
                .build();
        Hospedaje guardado = repositorioHospedaje.save(hospedaje);


        return CheckInResponse.builder()
                .hospedajeId(guardado.getId())
                .clienteId(cliente.getId())
                .cliente(cliente.getNombreCompleto())
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .tipoHabitacion(habitacion.getTipoHabitacion())
                .fechaHoraIngreso(guardado.getFechaHoraIngreso())
                .mensaje("Check-In realizado exitosamente. Habitación asignada: "
                        + habitacion.getNumeroHabitacion())
                .build();
    }
}