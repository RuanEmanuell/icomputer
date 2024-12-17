package com.daw.icomputer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.repository.ModeloPCRepository;
import com.daw.icomputer.repository.VendaRepository;

@Service
public class ModeloPCService {

    @Autowired
    private ModeloPCRepository modeloPCRepository;

    @Autowired
    private VendaRepository vendaRepository; // Injeta o repositório de vendas

    public Optional<ModeloPC> buscarModeloPorId(Integer idModelo) {
        return modeloPCRepository.findById(idModelo);
    }

    public ModeloPC criarModelo(ModeloPC modeloPC) {
        return modeloPCRepository.save(modeloPC);
    }

    public void deletarModelo(Integer idModelo) {
        Optional<ModeloPC> modeloOpt = modeloPCRepository.findById(idModelo);
    
        if (modeloOpt.isEmpty()) {
            throw new RuntimeException("Modelo com ID " + idModelo + " não encontrado");
        }
    
        ModeloPC modelo = modeloOpt.get();
        
        if (!vendaRepository.findByModelo(modelo).isEmpty()) {
            throw new RuntimeException("Não é possível excluir o modelo com ID " + idModelo + 
                                       " porque existem vendas associadas a ele.");
        }
    
        modeloPCRepository.delete(modelo);
    }
    

    public ModeloPC editarModelo(Integer idModelo, ModeloPC modeloPC) {
        return modeloPCRepository.findById(idModelo).map(modeloExistente -> {
            modeloExistente.setNome(modeloPC.getNome());
            modeloExistente.setCpu(modeloPC.getCpu());
            modeloExistente.setRam(modeloPC.getRam());
            modeloExistente.setSsd(modeloPC.getSsd());
            modeloExistente.setPreco(modeloPC.getPreco());
            return modeloPCRepository.save(modeloExistente);
        }).orElseThrow(() -> new RuntimeException("Modelo com ID " + idModelo + " não encontrado"));
    }

    public Page<ModeloPC> listarModelosPaginados(Pageable pageable) {
        return modeloPCRepository.findAll(pageable); 
    }
}
