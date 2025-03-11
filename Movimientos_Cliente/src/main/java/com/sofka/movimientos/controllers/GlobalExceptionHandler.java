package com.sofka.movimientos.controllers;

import com.sofka.movimientos.exceptions.ErrorRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ErrorRequest handleRuntimeException(RuntimeException ex) {
        ErrorRequest error = new ErrorRequest("Excepción en la aplicación", 500L, ex);
        return error;
    }

    @ExceptionHandler(Exception.class)
    public ErrorRequest handleGeneralException(Exception ex) {
        ErrorRequest error = new ErrorRequest("Error inesperado", 500L, ex);
        return error;
    }
}
