package com.example.Catalogo.service;

import com.example.Catalogo.dto.ImpressoraDTO;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.exception.DuplicateResourceException;
import com.example.Catalogo.model.Impressora;
import com.example.Catalogo.repository.ImpressoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Camada de serviço para operações de negócio relacionadas a Impressoras/Scanners.
 * Contém toda a lógica de negócio e validações necessárias.
 */
@Service
@Transactional
public class ImpressoraService {

    private final ImpressoraRepository impressoraRepository;

    @Autowired
    public ImpressoraService(ImpressoraRepository impressoraRepository) {
        this.impressoraRepository = impressoraRepository;
    }

    /**
     * Lista todas as impressoras cadastradas.
     * @return Lista de DTOs de impressoras
     */
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> listarTodas() {
        return impressoraRepository.findAll()
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma impressora por patrimônio.
     * @param patrimonio Número do patrimônio
     * @return DTO da impressora encontrada
     * @throws ResourceNotFoundException se a impressora não for encontrada
     */
    @Transactional(readOnly = true)
    public ImpressoraDTO buscarPorPatrimonio(String patrimonio) {
        Impressora impressora = impressoraRepository.findByPatrimonio(patrimonio)
                .orElseThrow(() -> new ResourceNotFoundException("Impressora não encontrada com patrimônio: " + patrimonio));
        return new ImpressoraDTO(impressora);
    }

    /**
     * Adiciona uma nova impressora ao inventário.
     * @param impressoraDTO Dados da impressora a ser adicionada
     * @return DTO da impressora criada
     * @throws DuplicateResourceException se já existe uma impressora com o mesmo patrimônio
     */
    public ImpressoraDTO adicionar(ImpressoraDTO impressoraDTO) {
        if (impressoraRepository.existsByPatrimonio(impressoraDTO.getPatrimonio())) {
            throw new DuplicateResourceException("Já existe uma impressora com o patrimônio: " + impressoraDTO.getPatrimonio());
        }

        Impressora impressora = impressoraDTO.toEntity();
        Impressora impressoraSalva = impressoraRepository.save(impressora);
        return new ImpressoraDTO(impressoraSalva);
    }

    /**
     * Atualiza uma impressora existente.
     * @param patrimonio Patrimônio da impressora a ser atualizada
     * @param impressoraDTO Novos dados da impressora
     * @return DTO da impressora atualizada
     * @throws ResourceNotFoundException se a impressora não for encontrada
     */
    public ImpressoraDTO atualizar(String patrimonio, ImpressoraDTO impressoraDTO) {
        Impressora impressoraExistente = impressoraRepository.findByPatrimonio(patrimonio)
                .orElseThrow(() -> new ResourceNotFoundException("Impressora não encontrada com patrimônio: " + patrimonio));

        // Atualiza os campos
        impressoraExistente.setTipo(impressoraDTO.getTipo());
        impressoraExistente.setLocalizacao(impressoraDTO.getLocalizacao());
        impressoraExistente.setStatus(impressoraDTO.getStatus());
        impressoraExistente.setFabricante(impressoraDTO.getFabricante());
        impressoraExistente.setModelo(impressoraDTO.getModelo());
        impressoraExistente.setIp(impressoraDTO.getIp());
        impressoraExistente.setPorta(impressoraDTO.getPorta());

        Impressora impressoraAtualizada = impressoraRepository.save(impressoraExistente);
        return new ImpressoraDTO(impressoraAtualizada);
    }

    /**
     * Exclui uma impressora do inventário.
     * @param patrimonio Patrimônio da impressora a ser excluída
     * @throws ResourceNotFoundException se a impressora não for encontrada
     */
    public void excluir(String patrimonio) {
        if (!impressoraRepository.existsByPatrimonio(patrimonio)) {
            throw new ResourceNotFoundException("Impressora não encontrada com patrimônio: " + patrimonio);
        }
        impressoraRepository.deleteById(patrimonio);
    }

    /**
     * Filtra impressoras com base em termo de pesquisa e filtros específicos.
     * @param termoPesquisa Termo para busca em patrimônio e modelo
     * @param filtros Mapa de filtros (tipo, status)
     * @return Lista de DTOs de impressoras filtradas
     */
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> filtrar(String termoPesquisa, Map<String, String> filtros) {
        String patrimonio = null;
        String modelo = null;
        String localizacao = null;
        
        // Se há termo de pesquisa, aplica em patrimônio e modelo
        if (termoPesquisa != null && !termoPesquisa.trim().isEmpty()) {
            patrimonio = termoPesquisa.trim();
            modelo = termoPesquisa.trim();
        }

        String tipo = filtros.get("tipo");
        String status = filtros.get("status");

        return impressoraRepository.findWithFilters(patrimonio, tipo, modelo, localizacao, status)
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca impressoras por tipo.
     * @param tipo Tipo da impressora (Impressora, Scanner, Multifuncional)
     * @return Lista de DTOs de impressoras do tipo especificado
     */
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> buscarPorTipo(String tipo) {
        return impressoraRepository.findByTipo(tipo)
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca impressoras por status.
     * @param status Status da impressora
     * @return Lista de DTOs de impressoras com o status especificado
     */
    @Transactional(readOnly = true)
    public List<ImpressoraDTO> buscarPorStatus(String status) {
        return impressoraRepository.findByStatus(status)
                .stream()
                .map(ImpressoraDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Conta o número total de impressoras por status.
     * @param status Status para contar
     * @return Quantidade de impressoras
     */
    @Transactional(readOnly = true)
    public long contarPorStatus(String status) {
        return impressoraRepository.countByStatus(status);
    }

    /**
     * Conta o número total de impressoras por tipo.
     * @param tipo Tipo para contar
     * @return Quantidade de impressoras
     */
    @Transactional(readOnly = true)
    public long contarPorTipo(String tipo) {
        return impressoraRepository.countByTipo(tipo);
    }

    /**
     * Obtém estatísticas básicas das impressoras.
     * @return Mapa com estatísticas (total, ativas, inativas, manutenção, por tipo)
     */
    @Transactional(readOnly = true)
    public Map<String, Long> obterEstatisticas() {
        long total = impressoraRepository.count();
        long ativas = impressoraRepository.countByStatus("Ativo");
        long inativas = impressoraRepository.countByStatus("Inativo");
        long manutencao = impressoraRepository.countByStatus("Manutenção");
        long impressoras = impressoraRepository.countByTipo("Impressora");
        long scanners = impressoraRepository.countByTipo("Scanner");
        long multifuncionais = impressoraRepository.countByTipo("Multifuncional");

        return Map.of(
                "total", total,
                "ativas", ativas,
                "inativas", inativas,
                "manutencao", manutencao,
                "impressoras", impressoras,
                "scanners", scanners,
                "multifuncionais", multifuncionais
        );
    }
}