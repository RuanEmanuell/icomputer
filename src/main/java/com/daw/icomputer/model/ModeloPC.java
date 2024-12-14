package com.daw.icomputer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "modelosPC")
public class ModeloPC {

    @Id
    @Column(name = "idModelo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModelo;

    private String nome;
    private String cpu;
    private Integer ram;
    private Integer ssd;
    private Float preco;

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getSsd() {
        return ssd;
    }

    public void setSsd(Integer ssd) {
        this.ssd = ssd;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }
}
