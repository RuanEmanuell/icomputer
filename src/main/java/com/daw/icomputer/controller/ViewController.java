package com.daw.icomputer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.model.Usuario;
import com.daw.icomputer.model.Venda;
import com.daw.icomputer.service.ModeloPCService;
import com.daw.icomputer.service.UsuarioService;
import com.daw.icomputer.service.VendasService;

@Controller
public class ViewController {

    @Autowired
    private UsuarioService usuariosService;

    @Autowired
    private ModeloPCService modeloPCService;

    @Autowired
    private VendasService vendasService;

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
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Order.asc("idUsuario")));

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
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Order.asc("idModelo")));

        Page<ModeloPC> pageModelos = modeloPCService.listarModelosPaginados(pageable);

        model.addAttribute("previousPageUrl", "/pages/fragment/lista-modelos?page=" + (pageModelos.getNumber() - 1) + "&size=" + pageModelos.getSize());
        model.addAttribute("nextPageUrl", "/pages/fragment/lista-modelos?page=" + (pageModelos.getNumber() + 1) + "&size=" + pageModelos.getSize());
        model.addAttribute("pageModelos", pageModelos);
        model.addAttribute("modelos", pageModelos.getContent());
        return "fragment/lista-modelos";
    }

    @GetMapping("/pages/vendas")
    public String retornarPaginaVendas() {
        return "vendas"; 
    }

    @GetMapping("/pages/fragment/lista-vendas")
    public String listarVendasHTML(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size, 
            Model model) {
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(Sort.Order.asc("idVenda")));

        Page<Venda> pageVendas = vendasService.listarVendasPaginadas(pageable);

        model.addAttribute("previousPageUrl", "/pages/fragment/lista-vendas?page=" + (pageVendas.getNumber() - 1) + "&size=" + pageVendas.getSize());
        model.addAttribute("nextPageUrl", "/pages/fragment/lista-vendas?page=" + (pageVendas.getNumber() + 1) + "&size=" + pageVendas.getSize());
        model.addAttribute("pageVendas", pageVendas);
        model.addAttribute("vendas", pageVendas.getContent());
        return "fragment/lista-vendas";
    }

}
