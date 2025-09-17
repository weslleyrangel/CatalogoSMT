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

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.service.ComputadorService;

/**
 * Controlador REST para operações CRUD de Computadores.
 * Expõe endpoints RESTful para gerenciar o inventário de computadores.
 */
@RestController
@RequestMapping("/api/computadores")
@CrossOrigin(origins = "*")
public class ComputadorController {

    private final ComputadorService computadorService;

    @Autowired
    public ComputadorController(ComputadorService computadorService) {
        this.computadorService = computadorService;
    }

    /**
     * Lista todos os computadores.
     * GET /api/computadores
     */
    @GetMapping
    public ResponseEntity<List<ComputadorDTO>> listarTodos() {
        List<ComputadorDTO> computadores = computadorService.listarTodos();
        return ResponseEntity.ok(computadores);
    }

    /**
     * Busca um computador específico por patrimônio.
     * GET /api/computadores/{patrimonio}
     */
    @GetMapping("/{patrimonio}")
    public ResponseEntity<ComputadorDTO> buscarPorPatrimonio(@PathVariable String patrimonio) {
        ComputadorDTO computador = computadorService.buscarPorPatrimonio(patrimonio);
        return ResponseEntity.ok(computador);
    }

    /**
     * Cria um novo computador.
     * POST /api/computadores
     */
    @PostMapping
    public ResponseEntity<ComputadorDTO> criar(@RequestBody ComputadorDTO computadorDTO) {
        ComputadorDTO novoComputador = computadorService.adicionar(computadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoComputador);
    }

    /**
     * Atualiza um computador existente.
     * PUT /api/computadores/{patrimonio}
     */
    @PutMapping("/{patrimonio}")
    public ResponseEntity<ComputadorDTO> atualizar(
            @PathVariable String patrimonio,
            @RequestBody ComputadorDTO computadorDTO) {
        ComputadorDTO computadorAtualizado = computadorService.atualizar(patrimonio, computadorDTO);
        return ResponseEntity.ok(computadorAtualizado);
    }

    /**
     * Exclui um computador.
     * DELETE /api/computadores/{patrimonio}
     */
    @DeleteMapping("/{patrimonio}")
    public ResponseEntity<Map<String, Object>> excluir(@PathVariable String patrimonio) {
        computadorService.excluir(patrimonio);
        
        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", true);
        response.put("mensagem", "Computador excluído com sucesso");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Filtra computadores com base em parâmetros de pesquisa.
     * GET /api/computadores/filtrar?termo={termo}&setor={setor}&usuario={usuario}&status={status}
     */
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

    /**
     * Lista computadores em manutenção.
     * GET /api/computadores/manutencao
     */
    @GetMapping("/manutencao")
    public ResponseEntity<List<ComputadorDTO>> listarEmManutencao() {
        List<ComputadorDTO> computadores = computadorService.obterEmManutencao();
        return ResponseEntity.ok(computadores);
    }

    /**
     * Obtém estatísticas dos computadores.
     * GET /api/computadores/estatisticas
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Long>> obterEstatisticas() {
        Map<String, Long> estatisticas = computadorService.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }

    /**
     * Conta computadores por status específico.
     * GET /api/computadores/contar/{status}
     */
    @GetMapping("/contar/{status}")
    public ResponseEntity<Map<String, Long>> contarPorStatus(@PathVariable String status) {
        long quantidade = computadorService.contarPorStatus(status);
        
        Map<String, Long> response = new HashMap<>();
        response.put("status", quantidade);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para buscar computadores por setor específico.
     * GET /api/computadores/setor/{setor}
     */
    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<ComputadorDTO>> buscarPorSetor(@PathVariable String setor) {
        Map<String, String> filtros = Map.of("setor", setor);
        List<ComputadorDTO> computadores = computadorService.filtrar(null, filtros);
        return ResponseEntity.ok(computadores);
    }

    /**
     * Endpoint para buscar computadores por usuário específico.
     * GET /api/computadores/usuario/{usuario}
     */
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<ComputadorDTO>> buscarPorUsuario(@PathVariable String usuario) {
        Map<String, String> filtros = Map.of("usuario", usuario);
        List<ComputadorDTO> computadores = computadorService.filtrar(null, filtros);
        return ResponseEntity.ok(computadores);
    }

    /**
     * Endpoint para pesquisa rápida por patrimônio ou usuário.
     * GET /api/computadores/pesquisar?q={termo}
     */
    @GetMapping("/pesquisar")
    public ResponseEntity<List<ComputadorDTO>> pesquisar(@RequestParam String q) {
        List<ComputadorDTO> computadores = computadorService.filtrar(q, new HashMap<>());
        return ResponseEntity.ok(computadores);
    }
}