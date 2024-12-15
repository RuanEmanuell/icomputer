package com.daw.icomputer.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.repository.ModeloPCRepository;

@Controller
@RequestMapping("/modelos")
@CrossOrigin(origins = "*") 
public class ModeloPCController {

    @Autowired
    private ModeloPCRepository modeloPCRepository;

    @GetMapping("/{idModelo}")
    public Optional<ModeloPC> buscarModeloPorId(@PathVariable Integer idModelo) {
        return modeloPCRepository.findById(idModelo);
    }

    @PostMapping
    public ModeloPC criarModelo(@RequestBody ModeloPC modeloPC) {
        return modeloPCRepository.save(modeloPC);
    }

    @DeleteMapping("/{idModelo}")
    public void deletarModelo(@PathVariable Integer idModelo) {
        modeloPCRepository.deleteById(idModelo);
    }

    @PutMapping("/{idModelo}")
    public ModeloPC editarModelo(@PathVariable Integer idModelo, @RequestBody ModeloPC modeloPC) {
        return modeloPCRepository.findById(idModelo).map(modeloExistente -> {
            modeloExistente.setNome(modeloPC.getNome());
            modeloExistente.setCpu(modeloPC.getCpu());
            modeloExistente.setRam(modeloPC.getRam());
            modeloExistente.setSsd(modeloPC.getSsd());
            modeloExistente.setPreco(modeloPC.getPreco());
    
            return modeloPCRepository.save(modeloExistente);
        }).orElseThrow(() -> new RuntimeException("Modelo com ID " + idModelo + " não encontrado"));
    }
}
