package com.daw.icomputer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

import com.daw.icomputer.model.Usuario;
import com.daw.icomputer.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{idUsuario}")
    public Optional<Usuario> buscarUsuarioPorId(@PathVariable Integer idUsuario) {
        return usuarioService.buscarUsuarioPorId(idUsuario);
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.checkEmailUsuarioExiste(usuario.getEmail()) == true) {
            return null;
        }
        return usuarioService.criarUsuario(usuario);
    }

    @DeleteMapping("/{idUsuario}")
    public void deletarUsuario(@PathVariable Integer idUsuario) {
        usuarioService.deletarUsuario(idUsuario);
    }

    @PutMapping("/{idUsuario}")
    public Usuario editarUsuario(@PathVariable Integer idUsuario, @RequestBody Usuario usuario) {
        return usuarioService.editarUsuario(idUsuario, usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");

        if (usuarioService.autenticarUsuario(email, senha)) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Login realizado com sucesso!"));
        } else {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "E-mail ou senha inválidos"));
        }

    }
}