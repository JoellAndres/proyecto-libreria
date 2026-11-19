package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.DTO.UsuarioRequestDTO;
import com.ExamenFinal.biblioteca.DTO.UsuarioResponseDTO;
import com.ExamenFinal.biblioteca.Entity.Usuario;
import com.ExamenFinal.biblioteca.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:access')") // Solo un bibliotecario puede listar a todos los usuarios
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{sub}")
    @PreAuthorize("hasAuthority('admin:access')") // Solo un bibliotecario busca usuarios específicos
    public ResponseEntity<Usuario> obtenerPorSub(@PathVariable String sub) {
        return ResponseEntity.ok(usuarioService.obtenerPorSub(sub));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }
}