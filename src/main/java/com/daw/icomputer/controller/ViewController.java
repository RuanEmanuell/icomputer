package com.daw.icomputer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.repository.ModeloPCRepository;

@Controller
public class ViewController {

    @Autowired
    private ModeloPCRepository modeloPCRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "index"; 
    }

    @GetMapping("/modelos/html")
    public String listarModelosHTML(Model model) {
        List<ModeloPC> modelos = modeloPCRepository.findAll();
        model.addAttribute("modelos", modelos);
        return "fragment/modelos"; 
    }
}
