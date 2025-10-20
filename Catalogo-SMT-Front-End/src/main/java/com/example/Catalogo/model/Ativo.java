/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author wesll Clase que representa um ativo de TI Contém os atributos comuns
 * a todos os tipos de ativos.
 */
@Entity
@Table(name = "Ativos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_ativo", discriminatorType = DiscriminatorType.STRING)

public abstract class Ativo {

    @Id
    @Column(name = "patrimonio", unique = true, nullable = false)
    private String patrimonio;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    public Ativo() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Ativo(String patrimonio, String status) {
        this();
        this.patrimonio = patrimonio;
        this.status = status;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ativo)) {
            return false;
        }
        Ativo ativo = (Ativo) o;
        return patrimonio != null && patrimonio.equals(ativo.patrimonio);
    }

    @Override
    public int hashCode() {
        return patrimonio != null ? patrimonio.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Ativo{"
                + "patrimonio= '" + patrimonio + "\'" + ", status = '" + status + "\'" + "}";
    }
}
