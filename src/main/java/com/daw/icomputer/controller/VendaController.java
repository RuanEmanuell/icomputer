package com.daw.icomputer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.daw.icomputer.model.Venda;
import com.daw.icomputer.service.VendasService;

@Controller
@RequestMapping("/vendas")
@CrossOrigin(origins = "*")
public class VendaController {

    @Autowired
    private VendasService vendasService;

    @GetMapping("/{idVenda}")
    public ResponseEntity<Optional<Venda>> buscarVendaPorId(@PathVariable Integer idVenda) {
        Optional<Venda> venda = vendasService.buscarVendaPorId(idVenda);
        return venda.isPresent() ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
        Venda novaVenda = vendasService.criarVenda(venda);
        return ResponseEntity.ok(novaVenda);
    }

    @DeleteMapping("/{idVenda}")
    public void deletarVenda(@PathVariable Integer idVenda) {
        vendasService.deletarVenda(idVenda);
    }

    @PutMapping("/{idVenda}")
    public ResponseEntity<Venda> editarVenda(@PathVariable Integer idVenda, @RequestBody Venda venda) {
        try {
            Venda vendaEditada = vendasService.editarVenda(idVenda, venda);
            return ResponseEntity.ok(vendaEditada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Venda>> listarVendasPaginadas(Pageable pageable) {
        Page<Venda> vendas = vendasService.listarVendasPaginadas(pageable);
        return ResponseEntity.ok(vendas);
    }
}
