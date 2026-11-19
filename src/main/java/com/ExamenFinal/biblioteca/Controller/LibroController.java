package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.DTO.LibroRequestDTO;
import com.ExamenFinal.biblioteca.DTO.LibroResponseDTO;
import com.ExamenFinal.biblioteca.Entity.Libro;
import com.ExamenFinal.biblioteca.Service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:access')")
    public ResponseEntity<List<Libro>> getLibros() {
        return ResponseEntity.ok(libroService.getLibros());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:access')")
    public ResponseEntity<LibroResponseDTO> saveLibro(@RequestBody LibroRequestDTO dto){
        return  ResponseEntity.ok(libroService.guardarLibro(dto));
    }
    
}