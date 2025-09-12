/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 *
 * @author wesll
 */
@Entity
@DiscriminatorValue("COMPUTADOR")
public class Computador extends Ativo{
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "setor")
    private String setor;
    
    @Column(name = "fabricante")
    private String fabricante;
    
    @Column(name = "modelo")
    private String modelo;
    
    @Column(name = "processador")
    private String processador;
    
    @Column(name = "ram")
    private String ram;
    
    @Column(name = "armazenamento")
    private String armazenamento;
    
    @Column(name = "sistema_operacional")
    private String os;
    
    public Computador(){
        super();
    }

    public Computador(String patrimonio, String status, String usuario, String setor) {
        super(patrimonio, status);
        this.usuario = usuario;
        this.setor = setor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
    
    @Override
     public String toString() {
        return "Computador{" +
                "patrimonio='" + getPatrimonio() + '\'' +
                ", usuario='" + usuario + '\'' +
                ", setor='" + setor + '\'' +
                ", status='" + getStatus() + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}
