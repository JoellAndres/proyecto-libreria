package com.ExamenFinal.biblioteca.Service;

import com.ExamenFinal.biblioteca.Entity.Autor;
import com.ExamenFinal.biblioteca.Repository.IAutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private IAutorRepository autorRepo;

    public List<Autor> getAutores(){
        List<Autor> allAutores = autorRepo.findAll();

        return allAutores;
    }

    public Autor saveAutor(Autor autor){
        return autorRepo.save(autor);
    }
}
