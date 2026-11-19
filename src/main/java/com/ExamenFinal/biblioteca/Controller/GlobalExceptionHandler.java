package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.DTO.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Atrapa todos los throw new RuntimeException("...") que pusimos en los Services
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex) {
        // Envolvemos el mensaje en nuestro DTO personalizado
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(), // Devuelve un código HTTP 400
                ex.getMessage()                 // Muestra tu mensaje: "El ejemplar ya está prestado", etc.
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Atrapa cualquier otro error inesperado (NullPointerExceptions, caídas de base de datos, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // Devuelve un código HTTP 500
                "Ocurrió un error inesperado en el servidor. Por favor, intente más tarde."
        );
        // Opcional: imprimir el error real en la consola de Spring para que lo veas vos
        ex.printStackTrace();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
