package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.Entity.Categoria;
import com.ExamenFinal.biblioteca.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getCategorias() {
        return ResponseEntity.ok(categoriaService.getCategorias());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:access')") // Protegido: Solo administrador crea categorías
    public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.saveCategoria(categoria));
    }
}