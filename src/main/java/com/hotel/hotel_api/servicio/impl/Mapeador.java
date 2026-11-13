package com.hotel.hotel_api.servicio.impl;

import com.hotel.hotel_api.dto.ClienteResponse;
import com.hotel.hotel_api.dto.FacturaResponse;
import com.hotel.hotel_api.dto.HabitacionResponse;
import com.hotel.hotel_api.entidad.Cliente;
import com.hotel.hotel_api.entidad.Factura;
import com.hotel.hotel_api.entidad.Habitacion;
import com.hotel.hotel_api.entidad.Hospedaje;

final class Mapeador {

    private Mapeador() {
    }

    static ClienteResponse aClienteResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .identificacion(cliente.getIdentificacion())
                .nombreCompleto(cliente.getNombreCompleto())
                .correoElectronico(cliente.getCorreoElectronico())
                .telefono(cliente.getTelefono())
                .fechaCreacion(cliente.getFechaCreacion())
                .build();
    }

    static HabitacionResponse aHabitacionResponse(Habitacion habitacion) {
        return HabitacionResponse.builder()
                .id(habitacion.getId())
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .piso(habitacion.getPiso())
                .tipoHabitacion(habitacion.getTipoHabitacion())
                .valorNoche(habitacion.getValorNoche())
                .estado(habitacion.getEstado())
                .build();
    }

    static FacturaResponse aFacturaResponse(Factura factura) {
        Hospedaje hospedaje = factura.getHospedaje();
        Cliente cliente = hospedaje.getCliente();
        Habitacion habitacion = hospedaje.getHabitacion();

        return FacturaResponse.builder()
                .facturaId(factura.getId())
                .clienteId(cliente.getId())
                .cliente(cliente.getNombreCompleto())
                .numeroHabitacion(habitacion.getNumeroHabitacion())
                .tipoHabitacion(habitacion.getTipoHabitacion())
                .fechaHoraIngreso(hospedaje.getFechaHoraIngreso())
                .fechaHoraSalida(hospedaje.getFechaHoraSalida())
                .cantidadNoches(factura.getCantidadNoches())
                .valorNoche(factura.getValorNoche())
                .valorTotal(factura.getValorTotal())
                .fechaGeneracion(factura.getFechaGeneracion())
                .build();
    }
}