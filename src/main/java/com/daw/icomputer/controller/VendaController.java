package com.daw.icomputer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.icomputer.model.Venda;
import com.daw.icomputer.repository.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @PostMapping
    public Venda criarVenda(@RequestBody Venda venda) {
        return vendaRepository.save(venda);
    }

    @DeleteMapping
    public void deletarVenda(@RequestBody Integer idVenda) {
        vendaRepository.deleteById(idVenda);
    }

    @PutMapping
    public void editarVenda(@RequestBody Venda venda) {
        vendaRepository.save(venda);
    }
}
