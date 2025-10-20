package com.example.Catalogo.dto;

import java.time.LocalDateTime;

public class AtividadeRecenteDTO {
    private String patrimonio;
    private String descricao;
    private LocalDateTime data;

    public AtividadeRecenteDTO(String patrimonio, String descricao, LocalDateTime data) {
        this.patrimonio = patrimonio;
        this.descricao = descricao;
        this.data = data;
    }

    // Getters e Setters
    public String getPatrimonio() { return patrimonio; }
    public void setPatrimonio(String patrimonio) { this.patrimonio = patrimonio; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
}