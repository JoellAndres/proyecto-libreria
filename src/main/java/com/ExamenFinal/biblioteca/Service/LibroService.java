package com.ExamenFinal.biblioteca.Service;

import com.ExamenFinal.biblioteca.DTO.LibroRequestDTO;
import com.ExamenFinal.biblioteca.DTO.LibroResponseDTO;
import com.ExamenFinal.biblioteca.Entity.Autor;
import com.ExamenFinal.biblioteca.Entity.Categoria;
import com.ExamenFinal.biblioteca.Entity.Libro;
import com.ExamenFinal.biblioteca.Repository.IAutorRepository;
import com.ExamenFinal.biblioteca.Repository.ICategoriaRepository;
import com.ExamenFinal.biblioteca.Repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService{

    @Autowired
    private ILibroRepository libroRepo;
    @Autowired
    private IAutorRepository autorRepo;
    @Autowired
    private ICategoriaRepository categoriaRepo;

    public List<Libro> getLibros(){
        List<Libro> allLibros = libroRepo.findAll();

        return allLibros;
    }

    public LibroResponseDTO guardarLibro(LibroRequestDTO libro){
        Libro libroNuevo = new Libro();
        libroNuevo.setTitulo(libro.getTitulo());
        libroNuevo.setCodigoLibro(libro.getCodigoLibro());

        //Busco los autores
        List<Autor> autores = autorRepo.findAllById(libro.getAutorIds());
        //Verifico que se hayan encontrado los autores
        if(autores.size() != libro.getAutorIds().size()){
            throw new RuntimeException("Error. No existen todos los IDs");
        }

        libroNuevo.setListaDeAutores(autores);

        //Busco categorías
        List<Categoria> categorias = categoriaRepo.findAllById(libro.getCategoriaIds());
        if(categorias.size() != libro.getCategoriaIds().size()){
            throw new RuntimeException("Error Categoría. No existen todos los IDs");
        }

        libroNuevo.setListaDeCategorias(categorias);

        Libro libroGuardado = libroRepo.save(libroNuevo);

        return covertirADto(libroGuardado);

    }

    private LibroResponseDTO covertirADto(Libro libro){

        LibroResponseDTO dto = new LibroResponseDTO();
        dto.setId(libro.getIdLibro());
        dto.setTitulo(libro.getTitulo());
        dto.setCodigoLibro(libro.getCodigoLibro());

        //Acá se va a guardar el nombre completo del autor
        String nombreCompletoAutor;

        for(Autor autor : libro.getListaDeAutores()){
            nombreCompletoAutor = autor.getNombre() + " " + autor.getApellido();
            dto.getNombresAutores().add(nombreCompletoAutor);
            nombreCompletoAutor = "";
        }

        //Acá se va a guardar la categoría
        for (Categoria categoria : libro.getListaDeCategorias()){
            dto.getNombresCategorias().add(categoria.getTipoCategoria());
        }

        return dto;

    }



}
