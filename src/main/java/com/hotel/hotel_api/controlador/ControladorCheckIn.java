package com.hotel.hotel_api.controlador;

import com.hotel.hotel_api.dto.CheckInRequest;
import com.hotel.hotel_api.dto.CheckInResponse;
import com.hotel.hotel_api.servicio.ServicioCheckIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkin")
@RequiredArgsConstructor
@Tag(name = "Check-In", description = "Registro de ingreso de clientes")
public class ControladorCheckIn {

    private final ServicioCheckIn servicioCheckIn;

    @PostMapping
    @Operation(summary = "Registrar ingreso: asigna automáticamente una habitación disponible del tipo solicitado")
    public ResponseEntity<CheckInResponse> realizarCheckIn(@Valid @RequestBody CheckInRequest request) {
        CheckInResponse respuesta = servicioCheckIn.realizarCheckIn(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}