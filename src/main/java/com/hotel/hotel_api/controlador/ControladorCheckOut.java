package com.hotel.hotel_api.controlador;

import com.hotel.hotel_api.dto.CheckOutRequest;
import com.hotel.hotel_api.dto.FacturaResponse;
import com.hotel.hotel_api.servicio.ServicioCheckOut;
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
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@Tag(name = "Check-Out", description = "Registro de salida y generación de factura")
public class ControladorCheckOut {

    private final ServicioCheckOut servicioCheckOut;

    @PostMapping
    @Operation(summary = "Registrar salida: libera la habitación y genera la factura")
    public ResponseEntity<FacturaResponse> realizarCheckOut(@Valid @RequestBody CheckOutRequest request) {
        FacturaResponse factura = servicioCheckOut.realizarCheckOut(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(factura);
    }
}