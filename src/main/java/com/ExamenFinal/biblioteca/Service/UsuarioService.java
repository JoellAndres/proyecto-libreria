package com.ExamenFinal.biblioteca.Service;
import com.ExamenFinal.biblioteca.DTO.UsuarioRequestDTO;
import com.ExamenFinal.biblioteca.DTO.UsuarioResponseDTO;
import com.ExamenFinal.biblioteca.Entity.Usuario;
import com.ExamenFinal.biblioteca.Repository.IUsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService{
    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        List<UsuarioResponseDTO> dtos = new ArrayList<>();
        for (Usuario u : usuarios) {
            dtos.add(convertirADto(u));
        }
        return dtos;
    }

    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {

        // 1. Verificamos si ya existe
        Optional<Usuario> existente = usuarioRepo.findById(dto.getAuth0Sub());
        if (existente.isPresent()) {
            // Si ya existe, simplemente lo devolvemos (no es un error)
            return convertirADto(existente.get());
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setAuth0Sub(dto.getAuth0Sub());
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setApellido(dto.getApellido());
        nuevoUsuario.setEmail(dto.getEmail());
        nuevoUsuario.setTelefono(dto.getTelefono());

        Usuario guardado = usuarioRepo.save(nuevoUsuario);
        return convertirADto(guardado);
    }

    @Transactional(readOnly = true)
    public Usuario obtenerPorSub(String sub) {
        // Al ser la Primary Key de tu entidad, findById funciona directamente con el String!
        return usuarioRepo.findById(sub)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));
    }

    //Metodo privado para mapear
    private UsuarioResponseDTO convertirADto(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setAuth0Sub(usuario.getAuth0Sub());
        dto.setNombreCompleto(usuario.getNombre() + " " + usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        return dto;
    }

}
