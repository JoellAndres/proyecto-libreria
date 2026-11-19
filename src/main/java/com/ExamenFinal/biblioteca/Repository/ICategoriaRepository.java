package com.ExamenFinal.biblioteca.Repository;

import com.ExamenFinal.biblioteca.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    // Busca coincidencias parciales en el nombre de la categoría, ignorando mayúsculas/minúsculas
    public List<Categoria> findByTipoCategoriaContainingIgnoreCase(String nombre);
}
