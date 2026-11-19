package com.ExamenFinal.biblioteca.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class LibroResponseDTO {
    private Long id;
    private String titulo;
    private String codigoLibro;
    private List<String> nombresAutores;
    private List<String> nombresCategorias;
}
