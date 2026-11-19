package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class PrestamoResponseDTO {
    private Long id;
    private String estado;
    private LocalDate fechaSalida;
    private LocalDate fechaDevolucionPrevista;
    private String TituloLibro;
}
