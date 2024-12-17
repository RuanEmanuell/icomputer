package com.daw.icomputer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "idUsuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nome;
    private String email;
    private String senha;
    private String endereco;
    private String permissaoAdmin;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPermissaoAdmin() {
        return permissaoAdmin;
    }

    public void setPermissaoAdmin(String permissaoAdmin) {
        this.permissaoAdmin = permissaoAdmin;
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + idUsuario +
            ", nome='" + nome + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

}
