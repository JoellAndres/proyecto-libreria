package com.ExamenFinal.biblioteca.Service;

import com.ExamenFinal.biblioteca.Entity.Categoria;
import com.ExamenFinal.biblioteca.Repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepo;

    public List<Categoria> getCategorias(){
        List<Categoria> allCategorias = categoriaRepo.findAll();

        return allCategorias;
    }

    public Categoria saveCategoria(Categoria categoria){
        return categoriaRepo.save(categoria);
    }


}
