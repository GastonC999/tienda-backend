package com.tutienda.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final String ERROR = "error";
    //Decide cómo convertir ese problema en una respuesta HTTP para el cliente.

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<?> handleStockInsuficiente(InsufficientStockException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                ERROR, ex.getMessage()
        ));
    }

    @ExceptionHandler({
            OrderNotFoundException.class,
            ProductNotFoundException.class
    })
    public ResponseEntity<?> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(ERROR, ex.getMessage()));
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<?> handleInvalidOrderStatusException(InvalidOrderStatusException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                ERROR, ex.getMessage()
        ));
    }
}