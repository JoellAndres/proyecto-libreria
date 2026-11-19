package com.ExamenFinal.biblioteca.Service;

import com.ExamenFinal.biblioteca.DTO.EjemplarResponseDTO;
import com.ExamenFinal.biblioteca.Entity.Ejemplar;
import com.ExamenFinal.biblioteca.Entity.Libro;
import com.ExamenFinal.biblioteca.Repository.IEjemplarRepository;
import com.ExamenFinal.biblioteca.Repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EjemplarService {
    @Autowired
    private IEjemplarRepository ejemplarRepo;
    @Autowired
    private ILibroRepository libroRepo;

    @Transactional
    public EjemplarResponseDTO crearEjemplar(Long idLibro, String numeroInventario){
        Libro libro = libroRepo.findById(idLibro).orElseThrow(() -> new RuntimeException("El libro no existe"));

        Ejemplar nuevoEjemplar = new Ejemplar();
        nuevoEjemplar.setLibro(libro);
        nuevoEjemplar.setNumeroInventario(numeroInventario);
        //Lo damos de alta
        nuevoEjemplar.setDisponible(true);
        nuevoEjemplar.setEstadoConserva("NUEVO");

        Ejemplar ejemplarGuardado = ejemplarRepo.save(nuevoEjemplar);

        return convertirADto(ejemplarGuardado);
    }

    @Transactional(readOnly = true)
    public List<EjemplarResponseDTO> obtenerPorLibro(Long idLibro) {
        
        List<Ejemplar> ejemplares = ejemplarRepo.findByLibro_IdLibro(idLibro);

        List<EjemplarResponseDTO> dtos = new ArrayList<>();
        for (Ejemplar e : ejemplares) {
            dtos.add(convertirADto(e));
        }
        return dtos;
    }

    @Transactional
    public EjemplarResponseDTO cambiarEstadoConserva(Long idEjemplar, String nuevoEstado) {
        Ejemplar ejemplar = ejemplarRepo.findById(idEjemplar)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));

        // Regla de Negocio: No se puede destruir o alterar un libro que no está en la biblioteca
        if (!ejemplar.getDisponible() && (nuevoEstado.equalsIgnoreCase("DESTRUIDO") || nuevoEstado.equalsIgnoreCase("PERDIDO"))) {
            throw new RuntimeException("No se puede marcar como destruido/perdido un ejemplar que actualmente está prestado.");
        }

        ejemplar.setEstadoConserva(nuevoEstado);

        // Si administrativamente se declara perdido o destruido, sale de circulación
        if (nuevoEstado.equalsIgnoreCase("DESTRUIDO") || nuevoEstado.equalsIgnoreCase("PERDIDO")) {
            ejemplar.setDisponible(false);
        }

        return convertirADto(ejemplar);
    }

    @Transactional
    public void darDeBajaEjemplar(Long idEjemplar) {
        Ejemplar ejemplar = ejemplarRepo.findById(idEjemplar)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));

        // Regla de Negocio: Para dar de baja definitiva, el libro debe estar en nuestro poder
        if (!ejemplar.getDisponible()) {
            throw new RuntimeException("No podés dar de baja un ejemplar que se encuentra prestado actualmente.");
        }

        ejemplar.setDisponible(false);
        ejemplar.setEstadoConserva("DADO_DE_BAJA");
    }

    private EjemplarResponseDTO convertirADto(Ejemplar ej){
        EjemplarResponseDTO dto = new EjemplarResponseDTO();

        dto.setIdEjemplar(ej.getIdEjemplar());
        dto.setTituloLibro(ej.getLibro().getTitulo());
        dto.setNumeroInventario(ej.getNumeroInventario());
        dto.setDisponible(ej.getDisponible());
        dto.setEstadoConserva(ej.getEstadoConserva());

        return dto;

    }


}
