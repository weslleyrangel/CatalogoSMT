package com.example.Catalogo.service;

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.exception.DuplicateResourceException;
import com.example.Catalogo.model.Computador;
import com.example.Catalogo.repository.ComputadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Camada de serviço para operações de negócio relacionadas a Computadores.
 * Contém toda a lógica de negócio e validações necessárias.
 */
@Service
@Transactional
public class ComputadorService {

    private final ComputadorRepository computadorRepository;

    @Autowired
    public ComputadorService(ComputadorRepository computadorRepository) {
        this.computadorRepository = computadorRepository;
    }

    /**
     * Lista todos os computadores cadastrados.
     * @return Lista de DTOs de computadores
     */
    @Transactional(readOnly = true)
    public List<ComputadorDTO> listarTodos() {
        return computadorRepository.findAll()
                .stream()
                .map(ComputadorDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um computador por patrimônio.
     * @param patrimonio Número do patrimônio
     * @return DTO do computador encontrado
     * @throws ResourceNotFoundException se o computador não for encontrado
     */
    @Transactional(readOnly = true)
    public ComputadorDTO buscarPorPatrimonio(String patrimonio) {
        Computador computador = computadorRepository.findByPatrimonio(patrimonio)
                .orElseThrow(() -> new ResourceNotFoundException("Computador não encontrado com patrimônio: " + patrimonio));
        return new ComputadorDTO(computador);
    }

    /**
     * Adiciona um novo computador ao inventário.
     * @param computadorDTO Dados do computador a ser adicionado
     * @return DTO do computador criado
     * @throws DuplicateResourceException se já existe um computador com o mesmo patrimônio
     */
    public ComputadorDTO adicionar(ComputadorDTO computadorDTO) {
        if (computadorRepository.existsByPatrimonio(computadorDTO.getPatrimonio())) {
            throw new DuplicateResourceException("Já existe um computador com o patrimônio: " + computadorDTO.getPatrimonio());
        }

        Computador computador = computadorDTO.toEntity();
        Computador computadorSalvo = computadorRepository.save(computador);
        return new ComputadorDTO(computadorSalvo);
    }

    /**
     * Atualiza um computador existente.
     * @param patrimonio Patrimônio do computador a ser atualizado
     * @param computadorDTO Novos dados do computador
     * @return DTO do computador atualizado
     * @throws ResourceNotFoundException se o computador não for encontrado
     */
    public ComputadorDTO atualizar(String patrimonio, ComputadorDTO computadorDTO) {
        Computador computadorExistente = computadorRepository.findByPatrimonio(patrimonio)
                .orElseThrow(() -> new ResourceNotFoundException("Computador não encontrado com patrimônio: " + patrimonio));

        // Atualiza os campos
        computadorExistente.setUsuario(computadorDTO.getUsuario());
        computadorExistente.setSetor(computadorDTO.getSetor());
        computadorExistente.setStatus(computadorDTO.getStatus());
        computadorExistente.setFabricante(computadorDTO.getFabricante());
        computadorExistente.setModelo(computadorDTO.getModelo());
        computadorExistente.setProcessador(computadorDTO.getProcessador());
        computadorExistente.setRam(computadorDTO.getRam());
        computadorExistente.setArmazenamento(computadorDTO.getArmazenamento());
        computadorExistente.setOs(computadorDTO.getOs());

        Computador computadorAtualizado = computadorRepository.save(computadorExistente);
        return new ComputadorDTO(computadorAtualizado);
    }

    /**
     * Exclui um computador do inventário.
     * @param patrimonio Patrimônio do computador a ser excluído
     * @throws ResourceNotFoundException se o computador não for encontrado
     */
    public void excluir(String patrimonio) {
        if (!computadorRepository.existsByPatrimonio(patrimonio)) {
            throw new ResourceNotFoundException("Computador não encontrado com patrimônio: " + patrimonio);
        }
        computadorRepository.deleteById(patrimonio);
    }

    /**
     * Filtra computadores com base em termo de pesquisa e filtros específicos.
     * @param termoPesquisa Termo para busca em patrimônio e usuário
     * @param filtros Mapa de filtros (setor, usuario, status)
     * @return Lista de DTOs de computadores filtrados
     */
    @Transactional(readOnly = true)
    public List<ComputadorDTO> filtrar(String termoPesquisa, Map<String, String> filtros) {
        String patrimonio = null;
        String usuario = null;
        
        // Se há termo de pesquisa, aplica tanto em patrimônio quanto usuário
        if (termoPesquisa != null && !termoPesquisa.trim().isEmpty()) {
            patrimonio = termoPesquisa.trim();
            usuario = termoPesquisa.trim();
        }

        String setor = filtros.get("setor");
        String status = filtros.get("status");
        
        // Se o filtro de usuário foi especificamente definido, usa ele
        if (filtros.containsKey("usuario") && filtros.get("usuario") != null && !filtros.get("usuario").trim().isEmpty()) {
            usuario = filtros.get("usuario");
        }

        return computadorRepository.findWithFilters(patrimonio, usuario, setor, status)
                .stream()
                .map(ComputadorDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtém todos os computadores que estão em manutenção.
     * @return Lista de DTOs de computadores em manutenção
     */
    @Transactional(readOnly = true)
    public List<ComputadorDTO> obterEmManutencao() {
        return computadorRepository.findEmManutencao()
                .stream()
                .map(ComputadorDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Conta o número total de computadores por status.
     * @param status Status para contar
     * @return Quantidade de computadores
     */
    @Transactional(readOnly = true)
    public long contarPorStatus(String status) {
        return computadorRepository.countByStatus(status);
    }

    /**
     * Obtém estatísticas básicas dos computadores.
     * @return Mapa com estatísticas (total, ativos, inativos, manutenção)
     */
    @Transactional(readOnly = true)
    public Map<String, Long> obterEstatisticas() {
        long total = computadorRepository.count();
        long ativos = computadorRepository.countByStatus("Ativo");
        long inativos = computadorRepository.countByStatus("Inativo");
        long manutencao = computadorRepository.countByStatus("Manutenção");

        return Map.of(
                "total", total,
                "ativos", ativos,
                "inativos", inativos,
                "manutencao", manutencao
        );
    }
}