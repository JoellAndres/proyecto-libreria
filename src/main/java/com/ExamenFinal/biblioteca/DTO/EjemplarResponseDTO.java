package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class EjemplarResponseDTO {
    private Long idEjemplar;
    private String tituloLibro;
    private String numeroInventario;
    private Boolean disponible;
    private String estadoConserva;
}
