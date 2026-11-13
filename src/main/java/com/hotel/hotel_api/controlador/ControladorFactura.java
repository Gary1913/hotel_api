package com.hotel.hotel_api.controlador;

import com.hotel.hotel_api.dto.FacturaResponse;
import com.hotel.hotel_api.servicio.ServicioFactura;
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
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "Consulta de facturas (inmutables)")
public class ControladorFactura {

    private final ServicioFactura servicioFactura;

    @GetMapping
    @Operation(summary = "Listar todas las facturas")
    public ResponseEntity<List<FacturaResponse>> consultarTodas() {
        return ResponseEntity.ok(servicioFactura.consultarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar factura por id")
    public ResponseEntity<FacturaResponse> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(servicioFactura.consultarPorId(id));
    }
}