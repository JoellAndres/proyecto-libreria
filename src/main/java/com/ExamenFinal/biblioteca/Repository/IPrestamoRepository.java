package com.ExamenFinal.biblioteca.Repository;

import com.ExamenFinal.biblioteca.Entity.Prestamo;
import com.ExamenFinal.biblioteca.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Devuelve 'true' si encuentra al menos un préstamo del usuario, en un estado específico, y con fecha de devolución menor a la actual
    boolean existsByUsuarioAndEstadoAndFechaDevolPrevistaBefore(Usuario usuario, String estado, LocalDate fechaActual);

    // NUEVO METODO: Trae todos los préstamos vinculados al Auth0 Sub de un usuario
    List<Prestamo> findByUsuario_Auth0Sub(String auth0Sub);
}
