package com.recetarioholon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recetarioholon.exception.RecetaNotFoundException;
import com.recetarioholon.model.Receta;
import com.recetarioholon.repository.RecetaRepository;


@RestController
@RequestMapping("/receta")
public class RecetaController {
    @Autowired
    private RecetaRepository recetaRepository;

    //Obtener todos
    @GetMapping
    public List<Receta> listarRecetas(){
        return recetaRepository.findAll();
    }

    //Nuevo
    @PostMapping
    public Receta newReceta(@RequestBody Receta newReceta){
        return recetaRepository.save(newReceta);
    }
    
    //Obtener uno
    @GetMapping("/{id}")
    public Receta one(@PathVariable Integer id){
        return recetaRepository.findById(id)
      .orElseThrow(() -> new RecetaNotFoundException(id));
    }

    // Editar
    @PutMapping
    public Receta edit(@RequestBody Receta newReceta, @PathVariable Integer id ){
        return recetaRepository.findById(id)
        .map(receta ->{
            receta.setTitulo(newReceta.getTitulo());
            receta.setSubtitulo(newReceta.getSubtitulo());
            receta.setIngredientes(newReceta.getIngredientes());
            receta.setPasos(newReceta.getPasos());
            return recetaRepository.save(receta);
        })
        .orElseThrow(()->new RecetaNotFoundException(id));
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        recetaRepository.deleteById(id);
    }

}
