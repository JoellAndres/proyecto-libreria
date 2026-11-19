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
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEjemplar;
    private String numeroInventario;
    private String estadoConserva;
    private Boolean disponible;

    @OneToMany(mappedBy = "ejemplar")
    @JsonIgnoreProperties("ejemplar")
    private List<Prestamo> listaDePrestamos;

    @ManyToOne
    @JoinColumn(
            name = "fk_idLibro",
            referencedColumnName = "idLibro"
    )
    @JsonIgnoreProperties("listaDeCategorias")
    private Libro libro;

}
