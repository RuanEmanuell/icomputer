package com.daw.icomputer.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @Column(name = "idVenda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenda;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idModelo", nullable = false)
    private ModeloPC modelo;

    @Temporal(TemporalType.DATE)
    @Column(name = "dataVenda")
    private Date dataVenda;

    private Float preco;

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ModeloPC getModelo() {
        return modelo;
    }

    public void setModelo(ModeloPC modelo) {
        this.modelo = modelo;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }
}
