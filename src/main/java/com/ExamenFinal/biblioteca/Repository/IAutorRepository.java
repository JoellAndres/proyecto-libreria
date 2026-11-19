package com.ExamenFinal.biblioteca.Repository;

import com.ExamenFinal.biblioteca.Entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAutorRepository extends JpaRepository<Autor, Long> {

    // Busca coincidencias parciales en nombre o apellido, ignorando mayúsculas/minúsculas
    public List<Autor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
}
