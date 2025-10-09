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
@DiscriminatorValue("IMPRESSORA")
public class Impressora extends Ativo {

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "localizacao")
    private String localizacao;

    @Column(name = "fabricante")
    private String fabricante;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "endereco_ip")
    private String ip;

    @Column(name = "porta")
    private String porta;

    public Impressora() {
        super();
    }

    public Impressora(String tipo, String localizacao, String patrimonio, String status) {
        super(patrimonio, status);
        this.tipo = tipo;
        this.localizacao = localizacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    @Override
    public String toString() {
        return "Impressora{"
                + "patrimonio='" + getPatrimonio() + '\''
                + ", tipo='" + tipo + '\''
                + ", localizacao='" + localizacao + '\''
                + ", status='" + getStatus() + '\''
                + ", fabricante='" + fabricante + '\''
                + ", modelo='" + modelo + '\''
                + '}';
    }
}
