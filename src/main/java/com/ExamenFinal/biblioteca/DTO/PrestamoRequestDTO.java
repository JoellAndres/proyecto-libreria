package com.ExamenFinal.biblioteca.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PrestamoRequestDTO {
    private String usuarioSub;
    private Long ejemplarId;
}
