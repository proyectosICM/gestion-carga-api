package com.icm.gestioncargaapi.components;

import com.icm.gestioncargaapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseBuilder {
    public ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        String error = status.getReasonPhrase();
        ErrorResponse errorResponse = new ErrorResponse(error, message);
        return new ResponseEntity<>(errorResponse, status);
    }

    public ResponseEntity<ErrorResponse> buildNotFoundError(String name) {

        return buildErrorResponse(HttpStatus.NOT_FOUND, "No se encontró " + name + " con el ID proporcionado");
    }

}
