package com.example.Catalogo.service;

import com.example.Catalogo.dto.ImpressoraDTO;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.exception.DuplicateResourceException;
import com.example.Catalogo.model.Impressora;
import com.example.Catalogo.repository.ImpressoraRepository;
import com.example.Catalogo.service.interfaces.IImpressoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImpressoraService implements IImpressoraService {
    private final ImpressoraRepository impressoraRepository;

    @Autowired
    public ImpressoraService(ImpressoraRepository impressoraRepository) {
        this.impressoraRepository = impressoraRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> listarTodas() {
        return impressoraRepository.findAll()
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ImpressoraDTO buscarPorPatrimonio(String patrimonio) {
        Impressora impressora = findImpressoraByPatrimonio(patrimonio);
        return new ImpressoraDTO(impressora);
    }

    @Override
    public ImpressoraDTO adicionar(ImpressoraDTO impressoraDTO) {
        validateNewImpressora(impressoraDTO);
        
        Impressora impressora = impressoraDTO.toEntity();
        Impressora impressoraSalva = impressoraRepository.save(impressora);
        return new ImpressoraDTO(impressoraSalva);
    }

    @Override
    public ImpressoraDTO atualizar(String patrimonio, ImpressoraDTO impressoraDTO) {
        Impressora impressoraExistente = findImpressoraByPatrimonio(patrimonio);
        
        updateImpressoraFields(impressoraExistente, impressoraDTO);
        
        Impressora impressoraAtualizada = impressoraRepository.save(impressoraExistente);
        return new ImpressoraDTO(impressoraAtualizada);
    }

    @Override
    public void excluir(String patrimonio) {
        if (!impressoraRepository.existsByPatrimonio(patrimonio)) {
            throw new ResourceNotFoundException("Impressora não encontrada com patrimônio: " + patrimonio);
        }
        impressoraRepository.deleteById(patrimonio);
    }

    /**
     * CORREÇÃO: O método foi simplificado para passar o termo de pesquisa
     * diretamente para a nova consulta do repositório.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> filtrar(String termoPesquisa, Map<String, String> filtros) {
        String tipo = filtros.get("tipo");
        String status = filtros.get("status");
        
        return impressoraRepository.findWithFilters(termoPesquisa, tipo, status)
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> buscarPorTipo(String tipo) {
        return impressoraRepository.findByTipo(tipo)
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> buscarPorStatus(String status) {
        return impressoraRepository.findByStatus(status)
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPorStatus(String status) {
        return impressoraRepository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPorTipo(String tipo) {
        return impressoraRepository.countByTipo(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obterEstatisticas() {
        return Map.of(
                "total", impressoraRepository.count(),
                "ativas", impressoraRepository.countByStatus("Ativo"),
                "inativas", impressoraRepository.countByStatus("Inativo"),
                "manutencao", impressoraRepository.countByStatus("Manutenção"),
                "impressoras", impressoraRepository.countByTipo("Impressora"),
                "scanners", impressoraRepository.countByTipo("Scanner"),
                "multifuncionais", impressoraRepository.countByTipo("Multifuncional")
        );
    }

    private Impressora findImpressoraByPatrimonio(String patrimonio) {
        return impressoraRepository.findByPatrimonio(patrimonio)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Impressora não encontrada com patrimônio: " + patrimonio));
    }

    private void validateNewImpressora(ImpressoraDTO impressoraDTO) {
        if (impressoraRepository.existsByPatrimonio(impressoraDTO.getPatrimonio())) {
            throw new DuplicateResourceException(
                "Já existe uma impressora com o patrimônio: " + impressoraDTO.getPatrimonio());
        }
    }

    private void updateImpressoraFields(Impressora existente, ImpressoraDTO dto) {
        existente.setTipo(dto.getTipo());
        existente.setLocalizacao(dto.getLocalizacao());
        existente.setStatus(dto.getStatus());
        existente.setFabricante(dto.getFabricante());
        existente.setModelo(dto.getModelo());
        existente.setIp(dto.getIp());
        existente.setPorta(dto.getPorta());
    }
}