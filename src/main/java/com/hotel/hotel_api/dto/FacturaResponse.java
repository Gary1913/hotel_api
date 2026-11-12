package com.hotel.hotel_api.dto;

import com.hotel.hotel_api.entidad.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaResponse {

    private Long facturaId;
    private Long clienteId;
    private String cliente;
    private Integer numeroHabitacion;
    private TipoHabitacion tipoHabitacion;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private Integer cantidadNoches;
    private BigDecimal valorNoche;
    private BigDecimal valorTotal;
    private LocalDateTime fechaGeneracion;
}