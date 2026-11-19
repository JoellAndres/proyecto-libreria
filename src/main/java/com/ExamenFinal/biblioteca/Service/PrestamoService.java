package com.ExamenFinal.biblioteca.Service;


import com.ExamenFinal.biblioteca.DTO.PrestamoRequestDTO;
import com.ExamenFinal.biblioteca.DTO.PrestamoResponseDTO;
import com.ExamenFinal.biblioteca.Entity.Ejemplar;
import com.ExamenFinal.biblioteca.Entity.Prestamo;
import com.ExamenFinal.biblioteca.Entity.Usuario;
import com.ExamenFinal.biblioteca.Repository.IEjemplarRepository;
import com.ExamenFinal.biblioteca.Repository.IPrestamoRepository;
import com.ExamenFinal.biblioteca.Repository.IUsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrestamoService {

    @Autowired
    private IEjemplarRepository ejemplarRepo;

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Autowired
    private IPrestamoRepository prestamoRepo;

    // Inyecta el valor desde application.properties. Si no lo encuentra, usa 14 por defecto.
    @Value("${biblioteca.prestamo.dias:14}")
    private int diasPrestamo;

    @Transactional
    public PrestamoResponseDTO crearPrestamo(PrestamoRequestDTO prestamoReqDTO){

        // 1. Buscamos el usuario
        Usuario usuario = usuarioRepo.findById(prestamoReqDTO.getUsuarioSub())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. VALIDACIÓN DE MOROSIDAD (Regla de negocio)
        // Verificamos si tiene libros sin devolver cuya fecha de vencimiento ya pasó
        boolean tieneMorosidad = prestamoRepo.existsByUsuarioAndEstadoAndFechaDevolPrevistaBefore(
                usuario, "ENTREGADO", LocalDate.now()
        );

        if (tieneMorosidad) {
            throw new RuntimeException("Préstamo denegado: El usuario tiene libros vencidos sin devolver.");
        }

        // 3. Buscamos el ejemplar
        Ejemplar ejemplar = ejemplarRepo.findById(prestamoReqDTO.getEjemplarId())
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));

        // 4. Chequeo de disponibilidad
        if(!ejemplar.getDisponible()){
            throw new RuntimeException("El ejemplar no se encuentra disponible actualmente.");
        }

        // 5. Armamos el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaSalida(LocalDate.now());
        prestamo.setFechaDevolPrevista(LocalDate.now().plusDays(diasPrestamo)); // Usamos la variable dinámica
        prestamo.setEstado("ENTREGADO"); // Sin espacios en blanco

        // 6. Modifico datos del ejemplar (Hibernate hace el UPDATE automáticamente por el @Transactional)
        ejemplar.setDisponible(false);

        Prestamo prestamoCreado = prestamoRepo.save(prestamo);
        return convertirResponseDTO(prestamoCreado);
    }

    @Transactional
    public PrestamoResponseDTO devolverPrestamo(Long idPrestamo) {
        // 1. Buscamos el préstamo en la base de datos
        Prestamo prestamo = prestamoRepo.findById(idPrestamo)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        // 2. Validamos que no esté devuelto ya
        if ("DEVUELTO".equalsIgnoreCase(prestamo.getEstado())) {
            throw new RuntimeException("Este préstamo ya fue registrado como devuelto anteriormente.");
        }

        // 3. Actualizamos los datos del préstamo
        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolReal(LocalDate.now());

        // 4. Liberamos el ejemplar para que vuelva a estar disponible en la biblioteca
        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setDisponible(true);
        ejemplarRepo.save(ejemplar);

        // Al tener @Transactional, Spring hace los UPDATE de Préstamo y Ejemplar automáticamente.
        return convertirResponseDTO(prestamo);
    }

    // NUEVO METODO: Trae el historial de préstamos de un usuario específico
    @Transactional(readOnly = true)
    public java.util.List<PrestamoResponseDTO> obtenerPrestamosDeUsuario(String sub) {
        java.util.List<Prestamo> misPrestamos = prestamoRepo.findByUsuario_Auth0Sub(sub);
        return misPrestamos.stream()
                .map(this::convertirResponseDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private PrestamoResponseDTO convertirResponseDTO(Prestamo prestamoGuardado){
        PrestamoResponseDTO prestamoRespDTO = new PrestamoResponseDTO();
        prestamoRespDTO.setId(prestamoGuardado.getIdPrestamo());

        // ¡IMPORTANTE! Agregamos el setEstado. El frontend de React lo lee para
        // saber si mostrar el cartel en Verde (DEVUELTO) o Amarillo (ENTREGADO)
        prestamoRespDTO.setEstado(prestamoGuardado.getEstado());

        prestamoRespDTO.setFechaSalida(prestamoGuardado.getFechaSalida());
        prestamoRespDTO.setFechaDevolucionPrevista(prestamoGuardado.getFechaDevolPrevista());
        prestamoRespDTO.setTituloLibro(prestamoGuardado.getEjemplar().getLibro().getTitulo());

        return prestamoRespDTO;
    }
}
