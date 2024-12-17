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

import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/email/{emailUsuario}")
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@PathVariable String emailUsuario) {
        return usuarioService.buscarUsuarioPorEmail(emailUsuario)
                .map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }
    

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.checkEmailUsuarioExiste(usuario.getEmail()) == true) {
            return null;
        }
        return usuarioService.criarUsuario(usuario);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer idUsuario, HttpSession session) {
    Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(idUsuario);

    if (usuarioOpt.isEmpty()) {
        return ResponseEntity.status(404).body(Map.of("success", false, "message", "Usuário não encontrado."));
    }

    Usuario usuario = usuarioOpt.get();

    // Pega o e-mail do usuário logado na sessão
    String emailUsuarioLogado = (String) session.getAttribute("emailUsuarioLogado");

    if (emailUsuarioLogado == null) {
        return ResponseEntity.status(401).body(Map.of("success", false, "message", "Usuário não autenticado."));
    }

    // Deleta o usuário
    usuarioService.deletarUsuario(idUsuario);

    // Se o usuário excluído é o mesmo logado, encerre a sessão
    if (usuario.getEmail().equals(emailUsuarioLogado)) {
        session.invalidate(); // Invalida a sessão
        return ResponseEntity.ok(Map.of("selfDelete", true, "message", "Você excluiu sua própria conta."));
    }

    return ResponseEntity.ok(Map.of("success", true, "message", "Usuário deletado com sucesso."));
}

    @PutMapping("/{idUsuario}")
    public Usuario editarUsuario(@PathVariable Integer idUsuario, @RequestBody Usuario usuario) {
        return usuarioService.editarUsuario(idUsuario, usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
    String email = credentials.get("email");
    String senha = credentials.get("senha");

    if (usuarioService.autenticarUsuario(email, senha)) {
        session.setAttribute("emailUsuarioLogado", email); // Salva o e-mail na sessão
        return ResponseEntity.ok(Map.of("success", true, "message", "Login realizado com sucesso!"));
    } else {
        return ResponseEntity.status(401).body(Map.of("success", false, "message", "E-mail ou senha inválidos"));
    }
}

}