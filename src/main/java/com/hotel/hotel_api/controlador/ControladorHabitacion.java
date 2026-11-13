package com.hotel.hotel_api.controlador;

import com.hotel.hotel_api.dto.HabitacionResponse;
import com.hotel.hotel_api.entidad.TipoHabitacion;
import com.hotel.hotel_api.servicio.ServicioHabitacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
@Tag(name = "Habitaciones", description = "Consulta de habitaciones y disponibilidad")
public class ControladorHabitacion {

    private final ServicioHabitacion servicioHabitacion;

    @GetMapping
    @Operation(summary = "Listar todas las habitaciones")
    public ResponseEntity<List<HabitacionResponse>> consultarTodas() {
        return ResponseEntity.ok(servicioHabitacion.consultarTodas());
    }

    @GetMapping("/{numero}")
    @Operation(summary = "Consultar habitación por número")
    public ResponseEntity<HabitacionResponse> consultarPorNumero(@PathVariable("numero") Integer numero) {
        return ResponseEntity.ok(servicioHabitacion.consultarPorNumero(numero));
    }

    @GetMapping("/disponibles/{tipo}")
    @Operation(summary = "Consultar habitaciones disponibles por tipo (ESTANDAR, PREMIER, SUITE)")
    public ResponseEntity<List<HabitacionResponse>> buscarDisponiblePorTipo(
            @PathVariable("tipo") TipoHabitacion tipo) {
        return ResponseEntity.ok(servicioHabitacion.buscarDisponiblePorTipo(tipo));
    }
}