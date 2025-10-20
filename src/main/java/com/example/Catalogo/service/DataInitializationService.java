package com.example.Catalogo.service;

import com.example.Catalogo.model.Computador;
import com.example.Catalogo.model.Impressora;
import com.example.Catalogo.repository.ComputadorRepository;
import com.example.Catalogo.repository.ImpressoraRepository;
import com.example.Catalogo.service.interfaces.IDataInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pela inicialização de dados de teste/exemplo. Refatorado
 * aplicando o princípio SRP - Single Responsibility Principle. Separado do
 * DataLoader para ter responsabilidade única.
 */
@Service
@Transactional
public class DataInitializationService implements IDataInitializationService {

    private final ComputadorRepository computadorRepository;
    private final ImpressoraRepository impressoraRepository;

    @Autowired
    public DataInitializationService(ComputadorRepository computadorRepository,
            ImpressoraRepository impressoraRepository) {
        this.computadorRepository = computadorRepository;
        this.impressoraRepository = impressoraRepository;
    }

    @Override
    public void initializeData() {
        if (!isDataAlreadyLoaded()) {
            carregarComputadores();
            carregarImpressoras();
            logDataLoadingStatus();
        }
    }

    @Override
    public boolean isDataAlreadyLoaded() {
        return computadorRepository.count() > 0 || impressoraRepository.count() > 0;
    }

    private void carregarComputadores() {
        ComputadorData[] computadoresData = getComputadoresTemplate();

        for (ComputadorData data : computadoresData) {
            Computador computador = createComputadorFromData(data);
            computadorRepository.save(computador);
        }
    }

    private void carregarImpressoras() {
        ImpressoraData[] impressorasData = getImpressorasTemplate();

        for (ImpressoraData data : impressorasData) {
            Impressora impressora = createImpressoraFromData(data);
            impressoraRepository.save(impressora);
        }
    }

    private Computador createComputadorFromData(ComputadorData data) {
        Computador computador = new Computador(data.patrimonio, data.status, data.usuario, data.setor);
        computador.setFabricante(data.fabricante);
        computador.setModelo(data.modelo);
        computador.setProcessador(data.processador);
        computador.setRam(data.ram);
        computador.setArmazenamento(data.armazenamento);
        computador.setOs(data.os);
        return computador;
    }

    private Impressora createImpressoraFromData(ImpressoraData data) {
        Impressora impressora = new Impressora(data.tipo, data.localizacao, data.patrimonio, data.status);
        impressora.setFabricante(data.fabricante);
        impressora.setModelo(data.modelo);
        impressora.setIp(data.ip);
        impressora.setPorta(data.porta);
        return impressora;
    }

    private ComputadorData[] getComputadoresTemplate() {
        return new ComputadorData[]{
            new ComputadorData("001TI2024", "João Silva", "TI", "Ativo",
            "Dell", "OptiPlex 7090", "Intel i7-11700", "16GB", "512GB SSD", "Windows 11 Pro"),
            new ComputadorData("002FIN2024", "Maria Souza", "Financeiro", "Ativo",
            "HP", "ProDesk 600 G6", "Intel i5-10500", "8GB", "256GB SSD", "Windows 10 Pro"),
            new ComputadorData("003VEN2024", "Carlos Oliveira", "Vendas", "Ativo",
            "Lenovo", "ThinkCentre M720q", "Intel i5-9400T", "8GB", "1TB HDD", "Windows 10 Pro"),
            new ComputadorData("004TI2024", "Ana Santos", "TI", "Manutenção",
            "Dell", "Vostro 3681", "Intel i3-10100", "8GB", "256GB SSD", "Windows 11 Home"),
            new ComputadorData("005FIN2024", "Roberto Lima", "Financeiro", "Ativo",
            "HP", "EliteDesk 800 G6", "Intel i7-10700", "16GB", "512GB SSD", "Windows 10 Pro"),
            new ComputadorData("006VEN2024", "Fernanda Costa", "Vendas", "Inativo",
            "Acer", "Aspire TC-895", "Intel i5-10400", "8GB", "1TB HDD", "Windows 10 Home"),
            new ComputadorData("007TI2024", "Pedro Alves", "TI", "Ativo",
            "Dell", "OptiPlex 5090", "Intel i5-11400", "8GB", "256GB SSD", "Windows 11 Pro"),
            new ComputadorData("008ADM2024", "Lucia Mendes", "Administrativo", "Ativo",
            "HP", "ProDesk 400 G7", "Intel i3-10100", "8GB", "500GB HDD", "Windows 10 Pro"),
            new ComputadorData("009RH2024", "Marcos Ferreira", "RH", "Manutenção",
            "Lenovo", "ThinkCentre M70q", "Intel i5-10400T", "8GB", "256GB SSD", "Windows 10 Pro"),
            new ComputadorData("010DIR2024", "Sandra Ribeiro", "Diretoria", "Ativo",
            "Dell", "OptiPlex 7090 Ultra", "Intel i7-11700T", "32GB", "1TB SSD", "Windows 11 Pro")
        };
    }

    private ImpressoraData[] getImpressorasTemplate() {
        return new ImpressoraData[]{
            new ImpressoraData("IMP001", "Multifuncional", "TI - Sala 101", "Ativo",
            "HP", "LaserJet Pro MFP M428fdw", "192.168.1.101", "9100"),
            new ImpressoraData("IMP002", "Impressora", "Financeiro - Sala 205", "Ativo",
            "Canon", "imageCLASS LBP6230dw", "192.168.1.102", "9100"),
            new ImpressoraData("IMP003", "Scanner", "RH - Sala 103", "Ativo",
            "Epson", "WorkForce DS-530", null, null),
            new ImpressoraData("IMP004", "Multifuncional", "Vendas - Sala 301", "Manutenção",
            "Brother", "DCP-L2540DW", "192.168.1.104", "9100"),
            new ImpressoraData("IMP005", "Impressora", "Diretoria - Sala 401", "Ativo",
            "HP", "Color LaserJet Pro M454dw", "192.168.1.105", "9100"),
            new ImpressoraData("IMP006", "Multifuncional", "Administrativo - Sala 201", "Ativo",
            "Canon", "imageCLASS MF445dw", "192.168.1.106", "9100"),
            new ImpressoraData("IMP007", "Scanner", "Arquivo - Subsolo", "Inativo",
            "Fujitsu", "ScanSnap iX1500", null, null),
            new ImpressoraData("IMP008", "Impressora", "Recepção", "Ativo",
            "HP", "DeskJet Plus 4120", "192.168.1.108", "9100")
        };
    }

    private void logDataLoadingStatus() {
        System.out.println("=== Dados iniciais carregados com sucesso! ===");
        System.out.println("Computadores: " + computadorRepository.count());
        System.out.println("Impressoras: " + impressoraRepository.count());
        System.out.println("===============================================");
    }


    private static class ComputadorData { //NOSONAR
        final String patrimonio, usuario, setor, status;
        final String fabricante, modelo, processador, ram, armazenamento, os;

        ComputadorData(String patrimonio, String usuario, String setor, String status,
                String fabricante, String modelo, String processador,
                String ram, String armazenamento, String os) {
            this.patrimonio = patrimonio;
            this.usuario = usuario;
            this.setor = setor;
            this.status = status;
            this.fabricante = fabricante;
            this.modelo = modelo;
            this.processador = processador;
            this.ram = ram;
            this.armazenamento = armazenamento;
            this.os = os;
        }
    }

    private static class ImpressoraData { //NOSONAR
        final String patrimonio, tipo, localizacao, status;
        final String fabricante, modelo, ip, porta;

        ImpressoraData(String patrimonio, String tipo, String localizacao, String status,
                String fabricante, String modelo, String ip, String porta) {
            this.patrimonio = patrimonio;
            this.tipo = tipo;
            this.localizacao = localizacao;
            this.status = status;
            this.fabricante = fabricante;
            this.modelo = modelo;
            this.ip = ip;
            this.porta = porta;
        }
    }
}
