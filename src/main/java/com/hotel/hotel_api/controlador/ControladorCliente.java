package com.hotel.hotel_api.controlador;

import com.hotel.hotel_api.dto.ClienteRequest;
import com.hotel.hotel_api.dto.ClienteResponse;
import com.hotel.hotel_api.servicio.ServicioCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Registro y consulta de clientes")
public class ControladorCliente {

    private final ServicioCliente servicioCliente;

    @PostMapping
    @Operation(summary = "Crear cliente")
    public ResponseEntity<ClienteResponse> crearCliente(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse respuesta = servicioCliente.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<List<ClienteResponse>> consultarTodos() {
        return ResponseEntity.ok(servicioCliente.consultarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar cliente por id")
    public ResponseEntity<ClienteResponse> consultarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(servicioCliente.consultarPorId(id));
    }

    @GetMapping("/documento/{identificacion}")
    @Operation(summary = "Consultar cliente por identificación")
    public ResponseEntity<ClienteResponse> consultarPorIdentificacion(
            @PathVariable("identificacion") String identificacion) {
        return ResponseEntity.ok(servicioCliente.consultarPorIdentificacion(identificacion));
    }
}