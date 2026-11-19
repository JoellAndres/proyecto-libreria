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
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLibro;
    private String titulo;
    private String codigoLibro;

    @ManyToMany()
    @JoinTable(
            //Nombre de la tercera tabla que se va a crear
            name = "autor_libro",

            //FK que apunta a ESTA misma clase
            joinColumns = @JoinColumn(
                    name = "fk_idLibro",
                    referencedColumnName = "idLibro"
            ),

            //FK que aputna a la otra tabla (Autor)
            inverseJoinColumns = @JoinColumn(
                    name = "fk_idAutor",
                    referencedColumnName = "idAutor"
            )
    )
    @JsonIgnoreProperties("listaDeLibros")
    private List<Autor> listaDeAutores;


    @ManyToMany()
    @JoinTable(
            //Nombre de la tercera tabla que se va a crear
            name = "libro_categoria",

            //FK que apunta a ESTA misma clase
            joinColumns = @JoinColumn(
                    name = "fk_idLibro",
                    referencedColumnName = "idLibro"

            ),

            //FK que apunta a la otra tabla (Categoria)
            inverseJoinColumns = @JoinColumn(
                    name = "fk_idCategoria",
                    referencedColumnName = "idCategoria"
            )
    )
    @JsonIgnoreProperties("listaDeLibrosCat")
    private List<Categoria> listaDeCategorias;

    @OneToMany(mappedBy = "libro")
    @JsonIgnoreProperties("libro")
    private List<Ejemplar> listaDeEjemplares;

}
