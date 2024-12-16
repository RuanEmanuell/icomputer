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
import com.daw.icomputer.service.ModeloPCService;

@Controller
@RequestMapping("/modelos")
@CrossOrigin(origins = "*")
public class ModeloPCController {

    @Autowired
    private ModeloPCService modeloPCService;

    @GetMapping("/{idModelo}")
    public Optional<ModeloPC> buscarModeloPorId(@PathVariable Integer idModelo) {
        return modeloPCService.buscarModeloPorId(idModelo);
    }

    @PostMapping
    public ModeloPC criarModelo(@RequestBody ModeloPC modeloPC) {
        return modeloPCService.criarModelo(modeloPC);
    }

    @DeleteMapping("/{idModelo}")
    public void deletarModelo(@PathVariable Integer idModelo) {
        modeloPCService.deletarModelo(idModelo);
    }

    @PutMapping("/{idModelo}")
    public ModeloPC editarModelo(@PathVariable Integer idModelo, @RequestBody ModeloPC modeloPC) {
        return modeloPCService.editarModelo(idModelo, modeloPC);
    }
}
