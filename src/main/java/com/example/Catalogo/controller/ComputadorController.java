package com.example.Catalogo.controller;

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.dto.HardwareInfoDTO;
import com.example.Catalogo.dto.HistoricoManutencaoDTO;
import com.example.Catalogo.model.Computador;
import com.example.Catalogo.repository.ComputadorRepository;
import com.example.Catalogo.repository.HistoricoManutencaoRepository;
import com.example.Catalogo.service.ComputadorService;
import com.example.Catalogo.service.interfaces.IHardwareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/computadores")
@CrossOrigin(origins = "*")
public class ComputadorController {

    private final ComputadorService computadorService;
    private final IHardwareInfoService hardwareInfoService;
    private final HistoricoManutencaoRepository historicoManutencaoRepository;
    private final ComputadorRepository computadorRepository;

    @Autowired
    public ComputadorController(ComputadorService computadorService, IHardwareInfoService hardwareInfoService, HistoricoManutencaoRepository historicoManutencaoRepository, ComputadorRepository computadorRepository) {
        this.computadorService = computadorService;
        this.hardwareInfoService = hardwareInfoService;
        this.historicoManutencaoRepository = historicoManutencaoRepository;
        this.computadorRepository = computadorRepository;
    }

    @GetMapping("/local-info")
    public ResponseEntity<HardwareInfoDTO> getLocalHardwareInfo() {
        HardwareInfoDTO hardwareInfo = hardwareInfoService.getLocalHardwareInfo();
        return ResponseEntity.ok(hardwareInfo);
    }
    @GetMapping
    public ResponseEntity<List<ComputadorDTO>> listarTodos() {
        List<ComputadorDTO> computadores = computadorService.listarTodos();
        return ResponseEntity.ok(computadores);
    }
    @GetMapping("/{patrimonio}")
    public ResponseEntity<ComputadorDTO> buscarPorPatrimonio(@PathVariable String patrimonio) {
        ComputadorDTO computador = computadorService.buscarPorPatrimonio(patrimonio);
        return ResponseEntity.ok(computador);
    }
    @PostMapping
    public ResponseEntity<ComputadorDTO> criar(@RequestBody ComputadorDTO computadorDTO) {
        ComputadorDTO novoComputador = computadorService.adicionar(computadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoComputador);
    }
    @PutMapping("/{patrimonio}")
    public ResponseEntity<ComputadorDTO> atualizar(
            @PathVariable String patrimonio,
            @RequestBody ComputadorDTO computadorDTO) {
        ComputadorDTO computadorAtualizado = computadorService.atualizar(patrimonio, computadorDTO);
        return ResponseEntity.ok(computadorAtualizado);
    }
    @DeleteMapping("/{patrimonio}")
    public ResponseEntity<Map<String, Object>> excluir(@PathVariable String patrimonio) {
        computadorService.excluir(patrimonio);

        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", true);
        response.put("mensagem", "Computador excluído com sucesso");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/filtrar")
    public ResponseEntity<List<ComputadorDTO>> filtrar(
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) String setor,
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) String status) {

        Map<String, String> filtros = new HashMap<>();
        if (setor != null && !setor.trim().isEmpty()) {
            filtros.put("setor", setor);
        }
        if (usuario != null && !usuario.trim().isEmpty()) {
            filtros.put("usuario", usuario);
        }
        if (status != null && !status.trim().isEmpty()) {
            filtros.put("status", status);
        }

        List<ComputadorDTO> computadores = computadorService.filtrar(termo, filtros);
        return ResponseEntity.ok(computadores);
    }
    @GetMapping("/manutencao")
    public ResponseEntity<List<ComputadorDTO>> listarEmManutencao() {
        List<ComputadorDTO> computadores = computadorService.obterEmManutencao();
        return ResponseEntity.ok(computadores);
    }
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Long>> obterEstatisticas() {
        Map<String, Long> estatisticas = computadorService.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
     @GetMapping("/contar/{status}")
    public ResponseEntity<Map<String, Long>> contarPorStatus(@PathVariable String status) {
        long quantidade = computadorService.contarPorStatus(status);

        Map<String, Long> response = new HashMap<>();
        response.put("status", quantidade);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<ComputadorDTO>> buscarPorSetor(@PathVariable String setor) {
        Map<String, String> filtros = Map.of("setor", setor);
        List<ComputadorDTO> computadores = computadorService.filtrar(null, filtros);
        return ResponseEntity.ok(computadores);
    }
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<ComputadorDTO>> buscarPorUsuario(@PathVariable String usuario) {
        Map<String, String> filtros = Map.of("usuario", usuario);
        List<ComputadorDTO> computadores = computadorService.filtrar(null, filtros);
        return ResponseEntity.ok(computadores);
    }
     @GetMapping("/pesquisar")
    public ResponseEntity<List<ComputadorDTO>> pesquisar(@RequestParam String q) {
        List<ComputadorDTO> computadores = computadorService.filtrar(q, new HashMap<>());
        return ResponseEntity.ok(computadores);
    }

    /**
     * CORREÇÃO: O método foi reescrito para buscar a entidade Computador real
     * do banco de dados antes de consultar o histórico.
     */
    @GetMapping("/{patrimonio}/historico")
    public ResponseEntity<List<HistoricoManutencaoDTO>> getHistoricoManutencao(@PathVariable String patrimonio) {
        return computadorRepository.findByPatrimonio(patrimonio)
                .map(computador -> {
                    List<HistoricoManutencaoDTO> historico = historicoManutencaoRepository
                            .findByComputadorOrderByDataEntradaDesc(computador)
                            .stream()
                            .map(HistoricoManutencaoDTO::new)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(historico);
                })
                .orElse(ResponseEntity.ok(Collections.emptyList()));
    }
}