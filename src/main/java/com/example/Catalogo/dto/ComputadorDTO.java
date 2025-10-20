package com.example.Catalogo.dto;

import com.example.Catalogo.model.Computador;
import java.time.LocalDateTime;

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
    private String detalhesManutencao;

    private String tipoDispositivo;
    private String placaMae;
    private String bios;
    private String memoriaDetalhes;

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
        
        this.tipoDispositivo = computador.getTipoDispositivo();
        this.placaMae = computador.getPlacaMae();
        this.bios = computador.getBios();
        this.memoriaDetalhes = computador.getMemoriaDetalhes();
    }

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

        computador.setTipoDispositivo(this.tipoDispositivo);
        computador.setPlacaMae(this.placaMae);
        computador.setBios(this.bios);
        computador.setMemoriaDetalhes(this.memoriaDetalhes);

        return computador;
    }

    public String getPatrimonio() { return patrimonio; }
    public void setPatrimonio(String patrimonio) { this.patrimonio = patrimonio; }

    public String getTipoDispositivo() { return tipoDispositivo; }
    public void setTipoDispositivo(String tipoDispositivo) { this.tipoDispositivo = tipoDispositivo; }
    public String getPlacaMae() { return placaMae; }
    public void setPlacaMae(String placaMae) { this.placaMae = placaMae; }
    public String getBios() { return bios; }
    public void setBios(String bios) { this.bios = bios; }
    public String getMemoriaDetalhes() { return memoriaDetalhes; }
    public void setMemoriaDetalhes(String memoriaDetalhes) { this.memoriaDetalhes = memoriaDetalhes; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getProcessador() { return processador; }
    public void setProcessador(String processador) { this.processador = processador; }
    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }
    public String getArmazenamento() { return armazenamento; }
    public void setArmazenamento(String armazenamento) { this.armazenamento = armazenamento; }
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public String getDetalhesManutencao() { return detalhesManutencao; }
    public void setDetalhesManutencao(String detalhesManutencao) { this.detalhesManutencao = detalhesManutencao; }
}