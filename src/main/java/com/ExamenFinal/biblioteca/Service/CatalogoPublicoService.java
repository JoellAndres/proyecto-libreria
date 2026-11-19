package com.ExamenFinal.biblioteca.Service;

import com.ExamenFinal.biblioteca.DTO.BusquedaGlobalDTO;
import com.ExamenFinal.biblioteca.Entity.Autor;
import com.ExamenFinal.biblioteca.Entity.Categoria;
import com.ExamenFinal.biblioteca.Entity.Libro;
import com.ExamenFinal.biblioteca.Repository.IAutorRepository;
import com.ExamenFinal.biblioteca.Repository.ICategoriaRepository;
import com.ExamenFinal.biblioteca.Repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogoPublicoService {
    @Autowired
    private ILibroRepository libroRepo;
    @Autowired
    private IAutorRepository autorRepo;
    @Autowired
    private ICategoriaRepository categoriaRepo;

    public List<BusquedaGlobalDTO> buscarOmnisearch(String termino){
        List<BusquedaGlobalDTO> resultadosUnificados = new ArrayList<>();

        if (termino == null || termino.trim().isEmpty()){
            return resultadosUnificados;
        }

        //Buscamos los libros
        List<Libro> libros = libroRepo.findByTituloContainingIgnoreCase(termino);
        for (Libro libro : libros){
            resultadosUnificados.add(
              new BusquedaGlobalDTO(libro.getIdLibro(), "LIBRO", libro.getTitulo(), "Codigo Libro: " + libro.getCodigoLibro())
            );
        }

        //Buscamos autores
        List<Autor> autores = autorRepo.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(termino, termino);
        for (Autor autor : autores) {
            resultadosUnificados.add(new BusquedaGlobalDTO(
                    autor.getIdAutor(),
                    "AUTOR",
                    autor.getNombre() + " " + autor.getApellido(),
                    "Autor del catálogo"
            ));
        }

        //Buscamos autores
        List<Categoria> categorias = categoriaRepo.findByTipoCategoriaContainingIgnoreCase(termino);
        for (Categoria categoria : categorias) {
            resultadosUnificados.add(new BusquedaGlobalDTO(
                    categoria.getIdCategoria(),
                    "CATEGORIA",
                    categoria.getTipoCategoria(),
                    "Género literario"
            ));
        }

        return resultadosUnificados;
    }


}
