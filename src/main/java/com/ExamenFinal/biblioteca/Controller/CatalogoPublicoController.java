package com.ExamenFinal.biblioteca.Controller;

import com.ExamenFinal.biblioteca.DTO.BusquedaGlobalDTO;
import com.ExamenFinal.biblioteca.Service.CatalogoPublicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo") // Esta será la ruta abierta a todo público
@CrossOrigin(origins = "*")
public class CatalogoPublicoController {

    @Autowired
    private CatalogoPublicoService catalogoPublicoService;

    // Endpoint: GET http://localhost:8080/api/catalogo/buscar?q=termino
    @GetMapping("/buscar")
    public ResponseEntity<List<BusquedaGlobalDTO>> busquedaGlobal(@RequestParam(name = "q") String termino) {
        return ResponseEntity.ok(catalogoPublicoService.buscarOmnisearch(termino));
    }
}