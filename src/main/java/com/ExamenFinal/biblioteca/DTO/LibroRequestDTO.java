package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class LibroRequestDTO {
    private String titulo;
    private String codigoLibro;
    private List<Long> autorIds;
    private List<Long> categoriaIds;
}
