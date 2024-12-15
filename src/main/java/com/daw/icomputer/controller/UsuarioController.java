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

import com.daw.icomputer.model.Usuario;
import com.daw.icomputer.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping
    public void deletarUsuario(@RequestBody Integer idUsuario){
        usuarioRepository.deleteById(idUsuario);
    }

    @PutMapping
    public void editarUsuario(@RequestBody Usuario usuario){
        usuarioRepository.save(usuario);
    }
}
