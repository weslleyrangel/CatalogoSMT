package com.example.Catalogo.controller;

import com.example.Catalogo.dto.AtividadeRecenteDTO;
import com.example.Catalogo.repository.ComputadorRepository;
import com.example.Catalogo.repository.HistoricoManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/atividades")
@CrossOrigin(origins = "*")
public class AtividadeController {

    private final HistoricoManutencaoRepository historicoManutencaoRepository;
    private final ComputadorRepository computadorRepository;

    @Autowired
    public AtividadeController(HistoricoManutencaoRepository historicoManutencaoRepository, ComputadorRepository computadorRepository) {
        this.historicoManutencaoRepository = historicoManutencaoRepository;
        this.computadorRepository = computadorRepository;
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<AtividadeRecenteDTO>> getAtividadesRecentes() {
        // 1. Busca as manutenções mais recentes
        Stream<AtividadeRecenteDTO> manutencoes = historicoManutencaoRepository.findTop5ByOrderByDataEntradaDesc()
                .stream()
                .map(h -> {
                    // CORREÇÃO: A descrição agora é o detalhe real do histórico
                    String descricao = h.getDetalhes() != null ? h.getDetalhes() : "Manutenção registrada.";
                    return new AtividadeRecenteDTO(
                        h.getComputador().getPatrimonio(),
                        descricao,
                        h.getDataEntrada()
                    );
                });

        // 2. Busca os computadores criados mais recentemente
        Stream<AtividadeRecenteDTO> novosComputadores = computadorRepository.findTop5ByOrderByDataCriacaoDesc()
                .stream()
                .map(c -> new AtividadeRecenteDTO(
                        c.getPatrimonio(),
                        "Novo computador cadastrado no sistema.", // Descrição para novos computadores
                        c.getDataCriacao()
                ));

        // 3. Combina, ordena e limita a lista final
        List<AtividadeRecenteDTO> atividadesCombinadas = Stream.concat(manutencoes, novosComputadores)
                .sorted(Comparator.comparing(AtividadeRecenteDTO::getData).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return ResponseEntity.ok(atividadesCombinadas);
    }
}