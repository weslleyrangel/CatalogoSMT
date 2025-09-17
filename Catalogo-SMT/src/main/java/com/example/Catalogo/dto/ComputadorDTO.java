package com.example.Catalogo.dto;

import java.time.LocalDateTime;

import com.example.Catalogo.model.Computador;

/**
 * Data Transfer Object para Computador.
 * Usado para transferir dados entre as camadas da aplicação e a API.
 */
public class ComputadorDTO {

    private String patrimonio;
    private String usuario;
    private String setor;
    private String status;
    private String fabricante;
    private String modelo;
    private String processador;
    private String ram;
    private String armazenamento;
    private String os;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // Constructors
    public ComputadorDTO() {}

    public ComputadorDTO(Computador computador) {
        this.patrimonio = computador.getPatrimonio();
        this.usuario = computador.getUsuario();
        this.setor = computador.getSetor();
        this.status = computador.getStatus();
        this.fabricante = computador.getFabricante();
        this.modelo = computador.getModelo();
        this.processador = computador.getProcessador();
        this.ram = computador.getRam();
        this.armazenamento = computador.getArmazenamento();
        this.os = computador.getOs();
        this.dataCriacao = computador.getDataCriacao();
        this.dataAtualizacao = computador.getDataAtualizacao();
    }

    /**
     * Converte o DTO em uma entidade Computador.
     * @return Nova instância de Computador
     */
    public Computador toEntity() {
        Computador computador = new Computador();
        computador.setPatrimonio(this.patrimonio);
        computador.setUsuario(this.usuario);
        computador.setSetor(this.setor);
        computador.setStatus(this.status);
        computador.setFabricante(this.fabricante);
        computador.setModelo(this.modelo);
        computador.setProcessador(this.processador);
        computador.setRam(this.ram);
        computador.setArmazenamento(this.armazenamento);
        computador.setOs(this.os);
        return computador;
    }

    // Getters and Setters
    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
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