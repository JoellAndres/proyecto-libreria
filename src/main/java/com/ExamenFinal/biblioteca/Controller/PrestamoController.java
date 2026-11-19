package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.DTO.PrestamoRequestDTO;
import com.ExamenFinal.biblioteca.DTO.PrestamoResponseDTO;
import com.ExamenFinal.biblioteca.Service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // NUEVO ENDPOINT: Extrae el Sub(ID) desde el token (Principal)
    // No lleva @PreAuthorize porque cualquier usuario validado puede ver los suyos.
    @GetMapping("/mis-prestamos")
    public ResponseEntity<java.util.List<PrestamoResponseDTO>> obtenerMisPrestamos(java.security.Principal principal) {
        String sub = principal.getName(); // Trae el auth0|xyz... directamente
        return ResponseEntity.ok(prestamoService.obtenerPrestamosDeUsuario(sub));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:access')") // Solo el administrador puede registrar la salida de un libro
    public ResponseEntity<PrestamoResponseDTO> crearPrestamo(@RequestBody PrestamoRequestDTO dto) {
        return ResponseEntity.ok(prestamoService.crearPrestamo(dto));
    }

    @PutMapping("/{id}/devolucion")
    @PreAuthorize("hasAuthority('admin:access')") // Solo el administrador puede registrar el retorno de un libro
    public ResponseEntity<PrestamoResponseDTO> procesarDevolucion(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverPrestamo(id));
    }
}