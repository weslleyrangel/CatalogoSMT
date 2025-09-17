package com.example.Catalogo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Catalogo.dto.ImpressoraDTO;
import com.example.Catalogo.service.ImpressoraService;

/**
 * Controlador REST para operações CRUD de Impressoras/Scanners.
 * Expõe endpoints RESTful para gerenciar o inventário de impressoras.
 */
@RestController
@RequestMapping("/api/impressoras")
@CrossOrigin(origins = "*")
public class ImpressoraController {

    private final ImpressoraService impressoraService;

    @Autowired
    public ImpressoraController(ImpressoraService impressoraService) {
        this.impressoraService = impressoraService;
    }

    /**
     * Lista todas as impressoras.
     * GET /api/impressoras
     */
    @GetMapping
    public ResponseEntity<List<ImpressoraDTO>> listarTodas() {
        List<ImpressoraDTO> impressoras = impressoraService.listarTodas();
        return ResponseEntity.ok(impressoras);
    }

    /**
     * Busca uma impressora específica por patrimônio.
     * GET /api/impressoras/{patrimonio}
     */
    @GetMapping("/{patrimonio}")
    public ResponseEntity<ImpressoraDTO> buscarPorPatrimonio(@PathVariable String patrimonio) {
        ImpressoraDTO impressora = impressoraService.buscarPorPatrimonio(patrimonio);
        return ResponseEntity.ok(impressora);
    }

    /**
     * Cria uma nova impressora.
     * POST /api/impressoras
     */
    @PostMapping
    public ResponseEntity<ImpressoraDTO> criar(@RequestBody ImpressoraDTO impressoraDTO) {
        ImpressoraDTO novaImpressora = impressoraService.adicionar(impressoraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaImpressora);
    }

    /**
     * Atualiza uma impressora existente.
     * PUT /api/impressoras/{patrimonio}
     */
    @PutMapping("/{patrimonio}")
    public ResponseEntity<ImpressoraDTO> atualizar(
            @PathVariable String patrimonio,
            @RequestBody ImpressoraDTO impressoraDTO) {
        ImpressoraDTO impressoraAtualizada = impressoraService.atualizar(patrimonio, impressoraDTO);
        return ResponseEntity.ok(impressoraAtualizada);
    }

    /**
     * Exclui uma impressora.
     * DELETE /api/impressoras/{patrimonio}
     */
    @DeleteMapping("/{patrimonio}")
    public ResponseEntity<Map<String, Object>> excluir(@PathVariable String patrimonio) {
        impressoraService.excluir(patrimonio);
        
        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", true);
        response.put("mensagem", "Impressora excluída com sucesso");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Filtra impressoras com base em parâmetros de pesquisa.
     * GET /api/impressoras/filtrar?termo={termo}&tipo={tipo}&status={status}
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<ImpressoraDTO>> filtrar(
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String status) {
        
        Map<String, String> filtros = new HashMap<>();
        if (tipo != null && !tipo.trim().isEmpty()) {
            filtros.put("tipo", tipo);
        }
        if (status != null && !status.trim().isEmpty()) {
            filtros.put("status", status);
        }

        List<ImpressoraDTO> impressoras = impressoraService.filtrar(termo, filtros);
        return ResponseEntity.ok(impressoras);
    }

    /**
     * Lista impressoras por tipo específico.
     * GET /api/impressoras/tipo/{tipo}
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ImpressoraDTO>> buscarPorTipo(@PathVariable String tipo) {
        List<ImpressoraDTO> impressoras = impressoraService.buscarPorTipo(tipo);
        return ResponseEntity.ok(impressoras);
    }

    /**
     * Lista impressoras por status específico.
     * GET /api/impressoras/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ImpressoraDTO>> buscarPorStatus(@PathVariable String status) {
        List<ImpressoraDTO> impressoras = impressoraService.buscarPorStatus(status);
        return ResponseEntity.ok(impressoras);
    }

    /**
     * Obtém estatísticas das impressoras.
     * GET /api/impressoras/estatisticas
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Long>> obterEstatisticas() {
        Map<String, Long> estatisticas = impressoraService.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }

    /**
     * Conta impressoras por status específico.
     * GET /api/impressoras/contar/status/{status}
     */
    @GetMapping("/contar/status/{status}")
    public ResponseEntity<Map<String, Long>> contarPorStatus(@PathVariable String status) {
        long quantidade = impressoraService.contarPorStatus(status);
        
        Map<String, Long> response = new HashMap<>();
        response.put("quantidade", quantidade);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Conta impressoras por tipo específico.
     * GET /api/impressoras/contar/tipo/{tipo}
     */
    @GetMapping("/contar/tipo/{tipo}")
    public ResponseEntity<Map<String, Long>> contarPorTipo(@PathVariable String tipo) {
        long quantidade = impressoraService.contarPorTipo(tipo);
        
        Map<String, Long> response = new HashMap<>();
        response.put("quantidade", quantidade);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para pesquisa rápida por patrimônio ou modelo.
     * GET /api/impressoras/pesquisar?q={termo}
     */
    @GetMapping("/pesquisar")
    public ResponseEntity<List<ImpressoraDTO>> pesquisar(@RequestParam String q) {
        List<ImpressoraDTO> impressoras = impressoraService.filtrar(q, new HashMap<>());
        return ResponseEntity.ok(impressoras);
    }

    /**
     * Lista todas as impressoras disponíveis (status ativo).
     * GET /api/impressoras/disponiveis
     */
    @GetMapping("/disponiveis")
    public ResponseEntity<List<ImpressoraDTO>> listarDisponiveis() {
        List<ImpressoraDTO> impressoras = impressoraService.buscarPorStatus("Ativo");
        return ResponseEntity.ok(impressoras);
    }

    /**
     * Lista impressoras em manutenção.
     * GET /api/impressoras/manutencao
     */
    @GetMapping("/manutencao")
    public ResponseEntity<List<ImpressoraDTO>> listarEmManutencao() {
        List<ImpressoraDTO> impressoras = impressoraService.buscarPorStatus("Manutenção");
        return ResponseEntity.ok(impressoras);
    }
}