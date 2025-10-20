package com.example.Catalogo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("COMPUTADOR")
public class Computador extends Ativo {
    
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

    // Novos campos
    @Column(name = "tipo_dispositivo")
    private String tipoDispositivo;

    @Column(name = "placa_mae")
    private String placaMae;

    @Column(name = "bios")
    private String bios;

    @Column(name = "memoria_detalhes")
    private String memoriaDetalhes;

    @OneToMany(mappedBy = "computador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HistoricoManutencao> historicoManutencoes = new ArrayList<>();

    public Computador() {
        super();
    }

    public Computador(String patrimonio, String status, String usuario, String setor) {
        super(patrimonio, status);
        this.usuario = usuario;
        this.setor = setor;
    }

    // Getters e Setters existentes...
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
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
    public List<HistoricoManutencao> getHistoricoManutencoes() { return historicoManutencoes; }
    public void setHistoricoManutencoes(List<HistoricoManutencao> historicoManutencoes) { this.historicoManutencoes = historicoManutencoes; }
    
    // Getters e Setters para os novos campos
    public String getTipoDispositivo() { return tipoDispositivo; }
    public void setTipoDispositivo(String tipoDispositivo) { this.tipoDispositivo = tipoDispositivo; }
    public String getPlacaMae() { return placaMae; }
    public void setPlacaMae(String placaMae) { this.placaMae = placaMae; }
    public String getBios() { return bios; }
    public void setBios(String bios) { this.bios = bios; }
    public String getMemoriaDetalhes() { return memoriaDetalhes; }
    public void setMemoriaDetalhes(String memoriaDetalhes) { this.memoriaDetalhes = memoriaDetalhes; }

    @Override
    public String toString() {
        return "Computador{" + "patrimonio='" + getPatrimonio() + '\'' + ", usuario='" + usuario + '\'' + '}';
    }
}