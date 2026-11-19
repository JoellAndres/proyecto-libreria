package com.ExamenFinal.biblioteca.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCategoria;
    private String tipoCategoria;

    //Relación muchos a muchos con Libro
    @ManyToMany(mappedBy = "listaDeCategorias")
    @JsonIgnoreProperties("listaDeCategorias")
    private List<Libro> listaDeLibrosCat;

}
