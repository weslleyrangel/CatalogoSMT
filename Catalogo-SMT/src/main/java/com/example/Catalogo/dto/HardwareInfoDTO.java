package com.example.Catalogo.dto;

/**
 * DTO (Data Transfer Object) para encapsular as informações de hardware
 * coletadas pela biblioteca OSHI.
 */
public class HardwareInfoDTO {

    private String os;
    private String fabricante;
    private String modelo;
    private String processador;
    private String ram;
    private String armazenamento;

    // Getters e Setters

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
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
}

