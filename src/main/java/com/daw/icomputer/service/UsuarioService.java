package com.daw.icomputer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.daw.icomputer.model.Usuario;
import com.daw.icomputer.repository.UsuarioRepository;
import com.daw.icomputer.repository.VendaRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VendaRepository vendaRepository; 

    public Optional<Usuario> buscarUsuarioPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String emailUsuario) {
        return usuarioRepository.findByEmail(emailUsuario);
    }

    public Usuario criarUsuario(Usuario usuario) {
        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        usuario.setSenha(criptografar.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Integer idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
    
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário com ID " + idUsuario + " não encontrado");
        }
    
        Usuario usuario = usuarioOpt.get();
    
        if (!vendaRepository.findByUsuario(usuario).isEmpty()) {
            throw new RuntimeException("Não é possível excluir o usuário com ID " + idUsuario + 
                                       " porque existem vendas associadas a ele.");
        }
    
        usuarioRepository.delete(usuario);
    }
    

    public Usuario editarUsuario(Integer idUsuario, Usuario usuario) {
        return usuarioRepository.findById(idUsuario).map(usuarioExistente -> {
            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setEndereco(usuario.getEndereco());
            usuarioExistente.setPermissaoAdmin(usuario.getPermissaoAdmin());
            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuário com ID " + idUsuario + " não encontrado"));
    }

    public Page<Usuario> listarUsuariosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public boolean checkEmailUsuarioExiste(String email){
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt.isPresent();
    }

    public boolean autenticarUsuario(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();
        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();

        return criptografar.matches(senha, usuario.getSenha());
    }
}
