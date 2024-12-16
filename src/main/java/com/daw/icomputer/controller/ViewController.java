package com.daw.icomputer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.model.Usuario;
import com.daw.icomputer.service.ModeloPCService;
import com.daw.icomputer.service.UsuarioService;

@Controller
public class ViewController {

    @Autowired
    private UsuarioService usuariosService;

    @Autowired
    private ModeloPCService modeloPCService;

    @GetMapping("/")
    public String retornarPaginaInicial() {
        return "index"; 
    }

    @GetMapping("/pages/usuarios")
    public String retornarPaginaUsuarios() {
        return "usuarios"; 
    }

    @GetMapping("/pages/fragment/lista-usuarios")
    public String listarUsuariosHTML(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size, 
            Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Usuario> pageUsuarios = usuariosService.listarUsuariosPaginados(pageable);

        model.addAttribute("previousPageUrl", "/pages/fragment/lista-usuarios?page=" + (pageUsuarios.getNumber() - 1) + "&size=" + pageUsuarios.getSize());
        model.addAttribute("nextPageUrl", "/pages/fragment/lista-usuarios?page=" + (pageUsuarios.getNumber() + 1) + "&size=" + pageUsuarios.getSize());
        model.addAttribute("pageUsuarios", pageUsuarios);
        model.addAttribute("usuarios", pageUsuarios.getContent());
        return "fragment/lista-usuarios";
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

        Page<ModeloPC> pageModelos = modeloPCService.listarModelosPaginados(pageable);

        model.addAttribute("previousPageUrl", "/pages/fragment/lista-modelos?page=" + (pageModelos.getNumber() - 1) + "&size=" + pageModelos.getSize());
        model.addAttribute("nextPageUrl", "/pages/fragment/lista-modelos?page=" + (pageModelos.getNumber() + 1) + "&size=" + pageModelos.getSize());
        model.addAttribute("pageModelos", pageModelos);
        model.addAttribute("modelos", pageModelos.getContent());
        return "fragment/lista-modelos";
    }

}
