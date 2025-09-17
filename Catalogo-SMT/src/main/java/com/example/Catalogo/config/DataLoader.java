/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Catalogo.model.Computador;
import com.example.Catalogo.model.Impressora;
import com.example.Catalogo.repository.ComputadorRepository;
import com.example.Catalogo.repository.ImpressoraRepository;

/**
 *
 * @author wesll
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final ComputadorRepository computadorRepository;
    private final ImpressoraRepository impressoraRepository;

    @Autowired
    public DataLoader(ComputadorRepository computadorRepository, ImpressoraRepository impressoraRepository) {
        this.computadorRepository = computadorRepository;
        this.impressoraRepository = impressoraRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        carregarComputadores();
        carregarImpressoras();
        
        System.out.println("=== Dados iniciais carregados com sucesso! ===");
        System.out.println("Computadores: " + computadorRepository.count());
        System.out.println("Impressoras: " + impressoraRepository.count());
        System.out.println("===============================================");
    }

    private void carregarComputadores() {
        // Verifica se já existem dados para não duplicar
        if (computadorRepository.count() > 0) {
            return;
        }

        // Computadores de exemplo baseados no frontend
        Computador[] computadores = {
            criarComputador("001TI2024", "João Silva", "TI", "Ativo", 
                "Dell", "OptiPlex 7090", "Intel i7-11700", "16GB", "512GB SSD", "Windows 11 Pro"),
                
            criarComputador("002FIN2024", "Maria Souza", "Financeiro", "Ativo",
                "HP", "ProDesk 600 G6", "Intel i5-10500", "8GB", "256GB SSD", "Windows 10 Pro"),
                
            criarComputador("003VEN2024", "Carlos Oliveira", "Vendas", "Ativo",
                "Lenovo", "ThinkCentre M720q", "Intel i5-9400T", "8GB", "1TB HDD", "Windows 10 Pro"),
                
            criarComputador("004TI2024", "Ana Santos", "TI", "Manutenção",
                "Dell", "Vostro 3681", "Intel i3-10100", "8GB", "256GB SSD", "Windows 11 Home"),
                
            criarComputador("005FIN2024", "Roberto Lima", "Financeiro", "Ativo",
                "HP", "EliteDesk 800 G6", "Intel i7-10700", "16GB", "512GB SSD", "Windows 10 Pro"),
                
            criarComputador("006VEN2024", "Fernanda Costa", "Vendas", "Inativo",
                "Acer", "Aspire TC-895", "Intel i5-10400", "8GB", "1TB HDD", "Windows 10 Home"),
                
            criarComputador("007TI2024", "Pedro Alves", "TI", "Ativo",
                "Dell", "OptiPlex 5090", "Intel i5-11400", "8GB", "256GB SSD", "Windows 11 Pro"),
                
            criarComputador("008ADM2024", "Lucia Mendes", "Administrativo", "Ativo",
                "HP", "ProDesk 400 G7", "Intel i3-10100", "8GB", "500GB HDD", "Windows 10 Pro"),
                
            criarComputador("009RH2024", "Marcos Ferreira", "RH", "Manutenção",
                "Lenovo", "ThinkCentre M70q", "Intel i5-10400T", "8GB", "256GB SSD", "Windows 10 Pro"),
                
            criarComputador("010DIR2024", "Sandra Ribeiro", "Diretoria", "Ativo",
                "Dell", "OptiPlex 7090 Ultra", "Intel i7-11700T", "32GB", "1TB SSD", "Windows 11 Pro")
        };

        for (Computador computador : computadores) {
            computadorRepository.save(computador);
        }
    }

    private void carregarImpressoras() {
        // Verifica se já existem dados para não duplicar
        if (impressoraRepository.count() > 0) {
            return;
        }

        // Impressoras de exemplo
        Impressora[] impressoras = {
            criarImpressora("IMP001", "Multifuncional", "TI - Sala 101", "Ativo",
                "HP", "LaserJet Pro MFP M428fdw", "192.168.1.101", "9100"),
                
            criarImpressora("IMP002", "Impressora", "Financeiro - Sala 205", "Ativo",
                "Canon", "imageCLASS LBP6230dw", "192.168.1.102", "9100"),
                
            criarImpressora("IMP003", "Scanner", "RH - Sala 103", "Ativo",
                "Epson", "WorkForce DS-530", null, null),
                
            criarImpressora("IMP004", "Multifuncional", "Vendas - Sala 301", "Manutenção",
                "Brother", "DCP-L2540DW", "192.168.1.104", "9100"),
                
            criarImpressora("IMP005", "Impressora", "Diretoria - Sala 401", "Ativo",
                "HP", "Color LaserJet Pro M454dw", "192.168.1.105", "9100"),
                
            criarImpressora("IMP006", "Multifuncional", "Administrativo - Sala 201", "Ativo",
                "Canon", "imageCLASS MF445dw", "192.168.1.106", "9100"),
                
            criarImpressora("IMP007", "Scanner", "Arquivo - Subsolo", "Inativo",
                "Fujitsu", "ScanSnap iX1500", null, null),
                
            criarImpressora("IMP008", "Impressora", "Recepção", "Ativo",
                "HP", "DeskJet Plus 4120", "192.168.1.108", "9100")
        };

        for (Impressora impressora : impressoras) {
            impressoraRepository.save(impressora);
        }
    }

    private Computador criarComputador(String patrimonio, String usuario, String setor, String status,
                                     String fabricante, String modelo, String processador, String ram,
                                     String armazenamento, String os) {
        Computador computador = new Computador(patrimonio, status, usuario, setor);
        computador.setFabricante(fabricante);
        computador.setModelo(modelo);
        computador.setProcessador(processador);
        computador.setRam(ram);
        computador.setArmazenamento(armazenamento);
        computador.setOs(os);
        return computador;
    }

    private Impressora criarImpressora(String patrimonio, String tipo, String localizacao, String status,
                                     String fabricante, String modelo, String ip, String porta) {
        Impressora impressora = new Impressora(patrimonio, status, tipo, localizacao);
        impressora.setFabricante(fabricante);
        impressora.setModelo(modelo);
        impressora.setIp(ip);
        impressora.setPorta(porta);
        return impressora;
    }
}
