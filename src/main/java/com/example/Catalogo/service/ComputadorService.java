package com.example.Catalogo.service;

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.exception.DuplicateResourceException;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.model.Computador;
import com.example.Catalogo.model.HistoricoManutencao;
import com.example.Catalogo.repository.ComputadorRepository;
import com.example.Catalogo.repository.HistoricoManutencaoRepository;
import com.example.Catalogo.service.interfaces.IComputadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComputadorService implements IComputadorService {

    private final ComputadorRepository computadorRepository;
    private final HistoricoManutencaoRepository historicoManutencaoRepository;

    @Autowired
    public ComputadorService(ComputadorRepository computadorRepository, HistoricoManutencaoRepository historicoManutencaoRepository) {
        this.computadorRepository = computadorRepository;
        this.historicoManutencaoRepository = historicoManutencaoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComputadorDTO> listarTodos() {
        return computadorRepository.findAll().stream().map(ComputadorDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ComputadorDTO buscarPorPatrimonio(String patrimonio) {
        Computador computador = findComputadorByPatrimonio(patrimonio);
        return new ComputadorDTO(computador);
    }

    /**
     * CORREÇÃO: Adicionada a lógica para criar um histórico de manutenção
     * se um novo computador for cadastrado diretamente com o status "Manutenção".
     */
    @Override
    public ComputadorDTO adicionar(ComputadorDTO computadorDTO) {
        validateNewComputador(computadorDTO);
        
        Computador computador = computadorDTO.toEntity();
        
        Computador computadorSalvo = computadorRepository.save(computador);

        if ("Manutenção".equals(computadorSalvo.getStatus())) {
            HistoricoManutencao novoHistorico = new HistoricoManutencao();
            novoHistorico.setDataEntrada(computadorSalvo.getDataCriacao());
            novoHistorico.setDetalhes(computadorDTO.getDetalhesManutencao());
            novoHistorico.setComputador(computadorSalvo);
            
            computadorSalvo.getHistoricoManutencoes().add(novoHistorico);
            computadorRepository.save(computadorSalvo);
        }

        return new ComputadorDTO(computadorSalvo);
    }

    @Override
    public ComputadorDTO atualizar(String patrimonio, ComputadorDTO computadorDTO) {
        Computador computadorExistente = findComputadorByPatrimonio(patrimonio);
        String statusAntigo = computadorExistente.getStatus();
        String statusNovo = computadorDTO.getStatus();

        if (!"Manutenção".equals(statusAntigo) && "Manutenção".equals(statusNovo)) {
            HistoricoManutencao novoHistorico = new HistoricoManutencao();
            novoHistorico.setDataEntrada(LocalDateTime.now());
            novoHistorico.setDetalhes(computadorDTO.getDetalhesManutencao());
            novoHistorico.setComputador(computadorExistente);
            computadorExistente.getHistoricoManutencoes().add(novoHistorico);
        }
        else if ("Manutenção".equals(statusAntigo) && !"Manutenção".equals(statusNovo)) {
            historicoManutencaoRepository.findTopByComputadorAndDataSaidaIsNullOrderByDataEntradaDesc(computadorExistente)
                .ifPresent(historico -> {
                    historico.setDataSaida(LocalDateTime.now());
                    String detalhesProblema = historico.getDetalhes() != null ? historico.getDetalhes() : "N/A";
                    String detalhesSolucao = computadorDTO.getDetalhesManutencao() != null ? computadorDTO.getDetalhesManutencao() : "N/A";
                    historico.setDetalhes("PROBLEMA:\n" + detalhesProblema + "\n\nSOLUÇÃO:\n" + detalhesSolucao);
                });
        }

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
        String status = filtros.get("status");
        return computadorRepository.findWithFilters(termoPesquisa, status).stream().map(ComputadorDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComputadorDTO> obterEmManutencao() {
        return computadorRepository.findEmManutencao().stream().map(ComputadorDTO::new).collect(Collectors.toList());
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

    private Computador findComputadorByPatrimonio(String patrimonio) {
        return computadorRepository.findByPatrimonio(patrimonio)
                .orElseThrow(() -> new ResourceNotFoundException("Computador não encontrado com patrimônio: " + patrimonio));
    }

    private void validateNewComputador(ComputadorDTO computadorDTO) {
        if (computadorRepository.existsByPatrimonio(computadorDTO.getPatrimonio())) {
            throw new DuplicateResourceException("Já existe um computador com o patrimônio: " + computadorDTO.getPatrimonio());
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
        existente.setTipoDispositivo(dto.getTipoDispositivo());
        existente.setPlacaMae(dto.getPlacaMae());
        existente.setBios(dto.getBios());
        existente.setMemoriaDetalhes(dto.getMemoriaDetalhes());
    }
}