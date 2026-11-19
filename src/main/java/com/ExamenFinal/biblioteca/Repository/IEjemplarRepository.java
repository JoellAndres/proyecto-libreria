package com.ExamenFinal.biblioteca.Repository;

import com.ExamenFinal.biblioteca.Entity.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEjemplarRepository extends JpaRepository<Ejemplar, Long> {

    List<Ejemplar> findByLibro_IdLibro(Long idLibro);


}



