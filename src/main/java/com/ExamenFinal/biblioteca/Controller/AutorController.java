package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.Entity.Autor;
import com.ExamenFinal.biblioteca.Service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ResponseEntity<List<Autor>> getAutores() {
        return ResponseEntity.ok(autorService.getAutores());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:access')") // Solo un bibliotecario puede listar a todos los usuarios
    public ResponseEntity<Autor> saveAutor(@RequestBody Autor autor) {
        return ResponseEntity.ok(autorService.saveAutor(autor));
    }
}