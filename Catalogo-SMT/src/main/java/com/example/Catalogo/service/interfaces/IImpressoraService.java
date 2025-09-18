/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.service.interfaces;

import com.example.Catalogo.dto.ImpressoraDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wesll
 */
public interface IImpressoraService {
    List<ImpressoraDTO> listarTodas();
    ImpressoraDTO buscarPorPatrimonio(String patrimonio);
    ImpressoraDTO adicionar(ImpressoraDTO impressoraDTO);
    ImpressoraDTO atualizar(String patrimonio, ImpressoraDTO impressoraDTO);
    void excluir(String patrimonio);
    List<ImpressoraDTO> filtrar(String termoPesquisa, Map<String, String> filtros);
    List<ImpressoraDTO> buscarPorTipo(String tipo);
    List<ImpressoraDTO> buscarPorStatus(String status);
    long contarPorStatus(String status);
    long contarPorTipo(String tipo);
    Map<String, Long> obterEstatisticas();
}
