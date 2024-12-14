package com.daw.icomputer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.repository.ModeloPCRepository;

@RestController
@RequestMapping("/modelos")
@CrossOrigin(origins = "*") 
public class ModeloPCController {

    @Autowired
    private ModeloPCRepository modeloPCRepository;

    @GetMapping
    public List<ModeloPC> listarModelos() {
        return modeloPCRepository.findAll();
    }

    @PostMapping
    public ModeloPC criarModelo(@RequestBody ModeloPC modeloPC) {
        return modeloPCRepository.save(modeloPC);
    }

    @DeleteMapping
    public void deletarModelo(@RequestBody Integer idModelo) {
        modeloPCRepository.deleteById(idModelo);
    }

    @PutMapping
    public void editarModelo(@RequestBody ModeloPC modeloPC) {
        modeloPCRepository.save(modeloPC);
    }
}
