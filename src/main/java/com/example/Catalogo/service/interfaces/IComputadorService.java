/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.service.interfaces;

import com.example.Catalogo.dto.ComputadorDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wesll
 */
public interface IComputadorService {
    List<ComputadorDTO> listarTodos();
    ComputadorDTO buscarPorPatrimonio(String patrimonio);
    ComputadorDTO adicionar(ComputadorDTO computadorDTO);
    ComputadorDTO atualizar(String patrimonio, ComputadorDTO computadorDTO);
    void excluir(String patrimonio);
    List<ComputadorDTO> filtrar(String termoPesquisa, Map<String, String> filtros);
    List<ComputadorDTO> obterEmManutencao();
    long contarPorStatus(String status);
    Map<String, Long> obterEstatisticas();
}
