package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ErrorResponseDTO {
    private int status;
    private String mensaje;
    private LocalDateTime timestamp;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now();
    }
}
