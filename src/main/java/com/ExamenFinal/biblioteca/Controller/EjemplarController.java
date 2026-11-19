package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.DTO.EjemplarResponseDTO;
import com.ExamenFinal.biblioteca.Service.EjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
@CrossOrigin(origins = "*")
public class EjemplarController {

    @Autowired
    private EjemplarService ejemplarService;

    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<EjemplarResponseDTO>> obtenerPorLibro(@PathVariable Long idLibro) {
        return ResponseEntity.ok(ejemplarService.obtenerPorLibro(idLibro));
    }

    @PostMapping("/libro/{idLibro}")
    @PreAuthorize("hasAuthority('admin:access')") // Solo el admin cambia el estado físico de conservación
    public ResponseEntity<EjemplarResponseDTO> crearEjemplar(
            @PathVariable Long idLibro,
            @RequestParam String numeroInventario) {
        return ResponseEntity.ok(ejemplarService.crearEjemplar(idLibro, numeroInventario));
    }

    @PutMapping("/{idEjemplar}/estado")
    @PreAuthorize("hasAuthority('admin:access')") // Solo el admin da de baja definitiva un ejemplar
    public ResponseEntity<EjemplarResponseDTO> cambiarEstado(
            @PathVariable Long idEjemplar,
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(ejemplarService.cambiarEstadoConserva(idEjemplar, nuevoEstado));
    }

    @DeleteMapping("/{idEjemplar}")
    @PreAuthorize("hasAuthority('admin:access')") // Solo el admin da de baja definitiva un ejemplar
    public ResponseEntity<Void> darDeBaja(@PathVariable Long idEjemplar) {
        ejemplarService.darDeBajaEjemplar(idEjemplar);
        return ResponseEntity.noContent().build();
    }
}