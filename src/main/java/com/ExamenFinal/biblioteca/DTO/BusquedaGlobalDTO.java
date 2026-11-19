package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BusquedaGlobalDTO {
    private Long idGlobalDTO;
    private String tipo;
    private String tituloPrincipal;
    private String descripcionSecundaria;

    public BusquedaGlobalDTO(Long idGlobalDTO, String tipo, String tituloPrincipal, String descripcionSecundaria) {
        this.idGlobalDTO = idGlobalDTO;
        this.tipo = tipo;
        this.tituloPrincipal = tituloPrincipal;
        this.descripcionSecundaria = descripcionSecundaria;
    }
}
