package com.ExamenFinal.biblioteca.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPrestamo;
    private String estado;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaSalida;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaDevolPrevista;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaDevolReal;

    @ManyToOne
    @JoinColumn(
            name = "fk_idEjemplar",
            referencedColumnName = "idEjemplar"
    )
    @JsonIgnoreProperties("listaDeEjemplares")
    private Ejemplar ejemplar;

    @ManyToOne
    @JoinColumn(
            name = "fk_idUsuario",
            referencedColumnName = "auth0_sub"
    )
    @JsonIgnoreProperties("listaDePrestamosUs")
    private Usuario usuario;
}
