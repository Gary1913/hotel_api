package com.hotel.hotel_api.dto;

import com.hotel.hotel_api.entidad.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInResponse {

    private Long hospedajeId;
    private Long clienteId;
    private String cliente;              // nombre completo del cliente
    private Integer numeroHabitacion;
    private TipoHabitacion tipoHabitacion;
    private LocalDateTime fechaHoraIngreso;
    private String mensaje;
}