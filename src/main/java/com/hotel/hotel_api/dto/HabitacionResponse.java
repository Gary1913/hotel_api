package com.hotel.hotel_api.dto;

import com.hotel.hotel_api.entidad.EstadoHabitacion;
import com.hotel.hotel_api.entidad.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitacionResponse {

    private Long id;
    private Integer numeroHabitacion;
    private Integer piso;
    private TipoHabitacion tipoHabitacion;
    private BigDecimal valorNoche;
    private EstadoHabitacion estado;
}