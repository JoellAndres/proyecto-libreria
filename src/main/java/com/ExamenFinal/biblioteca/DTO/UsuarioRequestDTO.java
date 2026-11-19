package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioRequestDTO {
    private String auth0Sub; // Coincide con tu @Id
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}
