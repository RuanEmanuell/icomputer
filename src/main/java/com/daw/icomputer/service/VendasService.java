package com.daw.icomputer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daw.icomputer.model.Venda;
import com.daw.icomputer.repository.VendaRepository;

import java.util.Optional;

@Service
public class VendasService {

    @Autowired
    private VendaRepository vendaRepository;

    public Optional<Venda> buscarVendaPorId(Integer idVenda) {
        return vendaRepository.findById(idVenda);
    }

    public Venda criarVenda(Venda venda) {
        return vendaRepository.save(venda);
    }

    public void deletarVenda(Integer idVenda) {
        if (vendaRepository.existsById(idVenda)) {
            vendaRepository.deleteById(idVenda);
        } else {
            throw new RuntimeException("Venda com ID " + idVenda + " não encontrada");
        }
    }

    public Venda editarVenda(Integer idVenda, Venda vendaAtualizada) {
        return vendaRepository.findById(idVenda).map(vendaExistente -> {
            vendaExistente.setUsuario(vendaAtualizada.getUsuario());
            vendaExistente.setModelo(vendaAtualizada.getModelo());
            vendaExistente.setDataVenda(vendaAtualizada.getDataVenda());
            return vendaRepository.save(vendaExistente);
        }).orElseThrow(() -> new RuntimeException("Venda com ID " + idVenda + " não encontrada"));
    }

    public Page<Venda> listarVendasPaginadas(Pageable pageable) {
        return vendaRepository.findAll(pageable);
    }
}
