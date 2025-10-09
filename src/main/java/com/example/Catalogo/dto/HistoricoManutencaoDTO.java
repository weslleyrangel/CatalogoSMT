package com.example.Catalogo.dto;

import com.example.Catalogo.model.HistoricoManutencao;
import java.time.LocalDateTime;

public class HistoricoManutencaoDTO {
    private Long id;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private String detalhes;
    
    public HistoricoManutencaoDTO() {}
    
    public HistoricoManutencaoDTO(HistoricoManutencao historico) {
        this.id = historico.getId();
        this.dataEntrada = historico.getDataEntrada();
        this.dataSaida = historico.getDataSaida();
        this.detalhes = historico.getDetalhes();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }
    public LocalDateTime getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }
    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
}