package com.daw.icomputer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.daw.icomputer.model.ModeloPC;
import com.daw.icomputer.repository.ModeloPCRepository;



@Service
public class ModeloPCService {

    @Autowired
    private ModeloPCRepository modeloPCRepository;

    public Optional<ModeloPC> buscarModeloPorId(Integer idModelo) {
        return modeloPCRepository.findById(idModelo);
    }

    public ModeloPC criarModelo(ModeloPC modeloPC) {
        return modeloPCRepository.save(modeloPC);
    }

    public void deletarModelo(Integer idModelo) {
        if (modeloPCRepository.existsById(idModelo)) {
            modeloPCRepository.deleteById(idModelo);
        } else {
            throw new RuntimeException("Modelo com ID " + idModelo + " não encontrado");
        }
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
