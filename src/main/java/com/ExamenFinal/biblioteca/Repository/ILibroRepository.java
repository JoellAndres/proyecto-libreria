package com.ExamenFinal.biblioteca.Repository;

import com.ExamenFinal.biblioteca.Entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {

    // Busca coincidencias parciales en el título, ignorando mayúsculas/minúsculas
    public List<Libro> findByTituloContainingIgnoreCase(String titulo);

}
