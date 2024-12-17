package com.daw.icomputer.repository;

import com.daw.icomputer.model.Venda;
import com.daw.icomputer.model.Usuario;
import com.daw.icomputer.model.ModeloPC;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByUsuario(Usuario usuario);

    List<Venda> findByModelo(ModeloPC modelo);
}
