package com.daw.icomputer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.repository.ModeloPCRepository;

@Controller
@RequestMapping("/modelos")
@CrossOrigin(origins = "*") 
public class ModeloPCController {

    @Autowired
    private ModeloPCRepository modeloPCRepository;

    @PostMapping
    public ModeloPC criarModelo(@RequestBody ModeloPC modeloPC) {
        return modeloPCRepository.save(modeloPC);
    }

    @DeleteMapping("/{idModelo}")
    public void deletarModelo(@PathVariable Integer idModelo) {
        modeloPCRepository.deleteById(idModelo);
    }

    @PutMapping
    public void editarModelo(@RequestBody ModeloPC modeloPC) {
        modeloPCRepository.save(modeloPC);
    }
}
