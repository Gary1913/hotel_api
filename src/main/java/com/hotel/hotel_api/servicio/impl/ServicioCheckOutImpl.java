package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.dto.CheckOutRequest;
import com.hotel.hotel_api.dto.FacturaResponse;
import com.hotel.hotel_api.entidad.EstadoHabitacion;
import com.hotel.hotel_api.entidad.Factura;
import com.hotel.hotel_api.entidad.Habitacion;
import com.hotel.hotel_api.entidad.Hospedaje;
import com.hotel.hotel_api.excepcion.CheckOutInvalidoException;
import com.hotel.hotel_api.excepcion.ClienteNoEncontradoException;
import com.hotel.hotel_api.excepcion.HospedajeActivoNoEncontradoException;
import com.hotel.hotel_api.repositorio.RepositorioCliente;
import com.hotel.hotel_api.repositorio.RepositorioFactura;
import com.hotel.hotel_api.repositorio.RepositorioHospedaje;
import com.hotel.hotel_api.servicio.ServicioCheckOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServicioCheckOutImpl implements ServicioCheckOut {

    private final RepositorioCliente repositorioCliente;
    private final RepositorioHospedaje repositorioHospedaje;
    private final RepositorioFactura repositorioFactura;

    @Override
    @Transactional
    public FacturaResponse realizarCheckOut(CheckOutRequest request) {
        Long clienteId = request.getClienteId();


        if (!repositorioCliente.existsById(clienteId)) {
            throw new ClienteNoEncontradoException("No existe un cliente con id " + clienteId);
        }


        Hospedaje hospedaje = repositorioHospedaje.findByClienteIdAndActivoTrue(clienteId)
                .orElseThrow(() -> new HospedajeActivoNoEncontradoException(
                        "El cliente con id " + clienteId + " no tiene un hospedaje activo"));

        Habitacion habitacion = hospedaje.getHabitacion();


        if (hospedaje.getFactura() != null) {
            throw new CheckOutInvalidoException(
                    "El hospedaje " + hospedaje.getId() + " ya tiene una factura generada");
        }
        if (habitacion.getEstado() != EstadoHabitacion.OCUPADA) {
            throw new CheckOutInvalidoException(
                    "La habitación " + habitacion.getNumeroHabitacion() + " no figura como OCUPADA");
        }


        LocalDateTime fechaHoraSalida = LocalDateTime.now();
        int cantidadNoches = CalculadoraNoches.calcularNoches(
                hospedaje.getFechaHoraIngreso(), fechaHoraSalida);
        BigDecimal valorNoche = habitacion.getValorNoche();
        BigDecimal valorTotal = valorNoche.multiply(BigDecimal.valueOf(cantidadNoches));


        hospedaje.setFechaHoraSalida(fechaHoraSalida);   // se registra la fecha de salida
        hospedaje.setActivo(false);                       // el hospedaje pasa a inactivo
        habitacion.setEstado(EstadoHabitacion.DISPONIBLE); // OCUPADA → DISPONIBLE


        Factura factura = Factura.builder()
                .hospedaje(hospedaje)
                .fechaGeneracion(fechaHoraSalida)
                .cantidadNoches(cantidadNoches)
                .valorNoche(valorNoche)
                .valorTotal(valorTotal)
                .build();
        Factura guardada = repositorioFactura.save(factura);

        return Mapeador.aFacturaResponse(guardada);
    }
}