package com.ExamenFinal.biblioteca.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @Column(name = "auth0_sub")
    private String auth0Sub;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnoreProperties("usuario")
    private List<Prestamo> listaDePrestamosUs;
}
