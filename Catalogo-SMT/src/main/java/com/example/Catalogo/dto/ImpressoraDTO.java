package com.example.Catalogo.dto;

import java.time.LocalDateTime;

import com.example.Catalogo.model.Impressora;

/**
 * Data Transfer Object para Impressora.
 * Usado para transferir dados entre as camadas da aplicação e a API.
 */
public class ImpressoraDTO {

    private String patrimonio;
    private String tipo;
    private String localizacao;
    private String status;
    private String fabricante;
    private String modelo;
    private String ip;
    private String porta;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // Constructors
    public ImpressoraDTO() {}

    public ImpressoraDTO(Impressora impressora) {
        this.patrimonio = impressora.getPatrimonio();
        this.tipo = impressora.getTipo();
        this.localizacao = impressora.getLocalizacao();
        this.status = impressora.getStatus();
        this.fabricante = impressora.getFabricante();
        this.modelo = impressora.getModelo();
        this.ip = impressora.getIp();
        this.porta = impressora.getPorta();
        this.dataCriacao = impressora.getDataCriacao();
        this.dataAtualizacao = impressora.getDataAtualizacao();
    }

    /**
     * Converte o DTO em uma entidade Impressora.
     * @return Nova instância de Impressora
     */
    public Impressora toEntity() {
        Impressora impressora = new Impressora();
        impressora.setPatrimonio(this.patrimonio);
        impressora.setTipo(this.tipo);
        impressora.setLocalizacao(this.localizacao);
        impressora.setStatus(this.status);
        impressora.setFabricante(this.fabricante);
        impressora.setModelo(this.modelo);
        impressora.setIp(this.ip);
        impressora.setPorta(this.porta);
        return impressora;
    }

    // Getters and Setters
    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}