package com.example.Catalogo.service;

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.exception.DuplicateResourceException;
import com.example.Catalogo.model.Computador;
import com.example.Catalogo.repository.ComputadorRepository;
import com.example.Catalogo.service.interfaces.IComputadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço de computadores refatorado implementando a interface IComputadorService.
 * Aplica os princípios DIP e SRP.
 */
@Service
@Transactional
public class ComputadorService implements IComputadorService {

    private final ComputadorRepository computadorRepository;

    @Autowired
    public ComputadorService(ComputadorRepository computadorRepository) {
        this.computadorRepository = computadorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComputadorDTO> listarTodos() {
        return computadorRepository.findAll()
                .stream()
                .map(ComputadorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ComputadorDTO buscarPorPatrimonio(String patrimonio) {
        Computador computador = findComputadorByPatrimonio(patrimonio);
        return new ComputadorDTO(computador);
    }

    @Override
    public ComputadorDTO adicionar(ComputadorDTO computadorDTO) {
        validateNewComputador(computadorDTO);
        
        Computador computador = computadorDTO.toEntity();
        Computador computadorSalvo = computadorRepository.save(computador);
        return new ComputadorDTO(computadorSalvo);
    }

    @Override
    public ComputadorDTO atualizar(String patrimonio, ComputadorDTO computadorDTO) {
        Computador computadorExistente = findComputadorByPatrimonio(patrimonio);
        
        updateComputadorFields(computadorExistente, computadorDTO);
        
        Computador computadorAtualizado = computadorRepository.save(computadorExistente);
        return new ComputadorDTO(computadorAtualizado);
    }

    @Override
    public void excluir(String patrimonio) {
        if (!computadorRepository.existsByPatrimonio(patrimonio)) {
            throw new ResourceNotFoundException("Computador não encontrado com patrimônio: " + patrimonio);
        }
        computadorRepository.deleteById(patrimonio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComputadorDTO> filtrar(String termoPesquisa, Map<String, String> filtros) {
        FilterCriteria criteria = buildFilterCriteria(termoPesquisa, filtros);
        
        return computadorRepository.findWithFilters(
                criteria.patrimonio, 
                criteria.usuario, 
                criteria.setor, 
                criteria.status
        ).stream()
         .map(ComputadorDTO::new)
         .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComputadorDTO> obterEmManutencao() {
        return computadorRepository.findEmManutencao()
                .stream()
                .map(ComputadorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPorStatus(String status) {
        return computadorRepository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obterEstatisticas() {
        return Map.of(
                "total", computadorRepository.count(),
                "ativos", computadorRepository.countByStatus("Ativo"),
                "inativos", computadorRepository.countByStatus("Inativo"),
                "manutencao", computadorRepository.countByStatus("Manutenção")
        );
    }

    // Métodos privados para melhor organização e reutilização
    private Computador findComputadorByPatrimonio(String patrimonio) {
        return computadorRepository.findByPatrimonio(patrimonio)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Computador não encontrado com patrimônio: " + patrimonio));
    }

    private void validateNewComputador(ComputadorDTO computadorDTO) {
        if (computadorRepository.existsByPatrimonio(computadorDTO.getPatrimonio())) {
            throw new DuplicateResourceException(
                "Já existe um computador com o patrimônio: " + computadorDTO.getPatrimonio());
        }
    }

    private void updateComputadorFields(Computador existente, ComputadorDTO dto) {
        existente.setUsuario(dto.getUsuario());
        existente.setSetor(dto.getSetor());
        existente.setStatus(dto.getStatus());
        existente.setFabricante(dto.getFabricante());
        existente.setModelo(dto.getModelo());
        existente.setProcessador(dto.getProcessador());
        existente.setRam(dto.getRam());
        existente.setArmazenamento(dto.getArmazenamento());
        existente.setOs(dto.getOs());
    }

    private FilterCriteria buildFilterCriteria(String termoPesquisa, Map<String, String> filtros) {
        FilterCriteria criteria = new FilterCriteria();
        
        // Aplica termo de pesquisa em patrimônio e usuário
        if (termoPesquisa != null && !termoPesquisa.trim().isEmpty()) {
            criteria.patrimonio = termoPesquisa.trim();
            criteria.usuario = termoPesquisa.trim();
        }

        // Aplica filtros específicos
        criteria.setor = filtros.get("setor");
        criteria.status = filtros.get("status");
        
        // Sobrescreve usuário se especificado nos filtros
        if (filtros.containsKey("usuario") && 
            filtros.get("usuario") != null && 
            !filtros.get("usuario").trim().isEmpty()) {
            criteria.usuario = filtros.get("usuario");
        }

        return criteria;
    }

    // Classe interna para critérios de filtro
    private static class FilterCriteria {
        String patrimonio;
        String usuario;
        String setor;
        String status;
    }
}