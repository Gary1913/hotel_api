package com.hotel.hotel_api.handler;

import com.hotel.hotel_api.excepcion.*;
import com.hotel.hotel_api.respuesta.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    // ===================== 404 - Recurso No Encontrado =====================
    @ExceptionHandler({
            ClienteNoEncontradoException.class,
            HabitacionNoEncontradaException.class,
            FacturaNoEncontradaException.class,
            HospedajeActivoNoEncontradoException.class
    })
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RuntimeException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, "Recurso No Encontrado", ex.getMessage());
    }

    // ===================== 409 - Conflicto de Negocio =====================
    @ExceptionHandler({
            ClienteConHospedajeActivoException.class,
            HabitacionNoDisponibleException.class,
            CheckOutInvalidoException.class,
            ClienteExisteException.class
    })
    public ResponseEntity<ErrorResponse> manejarConflicto(RuntimeException ex) {
        return construirRespuesta(HttpStatus.CONFLICT, "Conflicto de Negocio", ex.getMessage());
    }

    // ===================== 400 - Solicitud Inválida =====================

    // Falla de Bean Validation (@NotBlank, @Email, @Positive...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Solicitud Inválida", mensaje);
    }

    // JSON mal formado o valor inválido para un enum (ej: "tipoHabitacion": "PRESIDENCIAL")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Solicitud Inválida",
                "El cuerpo de la solicitud no es válido. Verifique el formato JSON y los valores enviados "
                        + "(tipoHabitacion permitido: ESTANDAR, PREMIER, SUITE)");
    }

    // Parámetro de la URL con tipo incorrecto (ej: /api/clientes/abc o /disponibles/PRESIDENCIAL)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> manejarTipoInvalido(MethodArgumentTypeMismatchException ex) {
        String mensaje = "El valor '" + ex.getValue() + "' no es válido para el parámetro '" + ex.getName() + "'";
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Solicitud Inválida", mensaje);
    }




    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> manejarRutaNoExiste(NoResourceFoundException ex) {
        return construirRespuesta(HttpStatus.NOT_FOUND, "Recurso No Encontrado",
                "La ruta '/" + ex.getResourcePath() + "' no existe");
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> manejarMetodoNoPermitido(HttpRequestMethodNotSupportedException ex) {
        return construirRespuesta(HttpStatus.METHOD_NOT_ALLOWED, "Método No Permitido",
                "El método " + ex.getMethod() + " no está permitido para esta ruta");
    }

    // ===================== 500 - Error Interno del Servidor =====================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErrorGeneral(Exception ex) {
        log.error("Error inesperado", ex);
        return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Error Interno del Servidor",
                "Ocurrió un error inesperado. Intente nuevamente más tarde");
    }


    private ResponseEntity<ErrorResponse> construirRespuesta(HttpStatus estado, String error, String mensaje) {
        ErrorResponse cuerpo = ErrorResponse.builder()
                .codigoHttp(estado.value())
                .error(error)
                .mensaje(mensaje)
                .fechaHora(LocalDateTime.now())
                .build();
        return ResponseEntity.status(estado).body(cuerpo);
    }
}