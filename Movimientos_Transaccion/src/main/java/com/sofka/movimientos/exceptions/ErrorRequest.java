package com.sofka.movimientos.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorRequest {
    private String errorMessage;
    private Long errorCode;
    private Throwable errorCause;

    public ErrorRequest(String errorMessage, Long errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
