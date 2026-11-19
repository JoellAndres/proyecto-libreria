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
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAutor;
    private String nombre;
    private String apellido;

    //Relación muchos a muchos con Libro
    @ManyToMany(mappedBy = "listaDeAutores")
    @JsonIgnoreProperties("listaDeAutores")
    private List<Libro> listaDeLibros;
}
