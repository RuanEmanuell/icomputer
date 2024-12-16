package com.daw.icomputer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.repository.ModeloPCRepository;

@Controller
public class ViewController {

    @Autowired
    private ModeloPCRepository modeloPCRepository;

    @GetMapping("/")
    public String retornarPaginaInicial() {
        return "index"; 
    }

    @GetMapping("/pages/modelos")
    public String retornarPaginaModelos() {
        return "modelos"; 
    }

    @GetMapping("/pages/fragment/lista-modelos")
    public String listarModelosHTML(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size, 
            Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ModeloPC> pageModelos = modeloPCRepository.findAll(pageable);

        model.addAttribute("previousPageUrl", "/pages/fragment/lista-modelos?page=" + (pageModelos.getNumber() - 1) + "&size=" + pageModelos.getSize());
        model.addAttribute("nextPageUrl", "/pages/fragment/lista-modelos?page=" + (pageModelos.getNumber() + 1) + "&size=" + pageModelos.getSize());
        model.addAttribute("pageModelos", pageModelos);
        model.addAttribute("modelos", pageModelos.getContent());
        return "fragment/lista-modelos";
    }

}
