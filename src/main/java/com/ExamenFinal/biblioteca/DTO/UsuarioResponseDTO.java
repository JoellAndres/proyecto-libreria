package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioResponseDTO {
    private String auth0Sub;
    private String nombreCompleto;
    private String email;
    private String telefono;
}
