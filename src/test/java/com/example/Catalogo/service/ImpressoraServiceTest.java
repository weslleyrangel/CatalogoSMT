package com.example.Catalogo.service;

import com.example.Catalogo.dto.ImpressoraDTO;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.model.Impressora;
import com.example.Catalogo.repository.ImpressoraRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImpressoraServiceTest {

    @Mock
    private ImpressoraRepository impressoraRepository;

    @InjectMocks
    private ImpressoraService impressoraService;

    @Test
    @DisplayName("Deve retornar ImpressoraDTO ao buscar por patrimônio existente")
    void deveRetornarImpressoraDTO_QuandoBuscarPorPatrimonioExistente() {
        // Arrange (Preparação)
        String patrimonio = "IMP001";
        String tipo = "Multifuncional";
        String localizacao = "TI";
        String status = "Ativo";

        Impressora impressora = new Impressora(tipo, localizacao, patrimonio, status);
        when(impressoraRepository.findByPatrimonio(patrimonio)).thenReturn(Optional.of(impressora));

        ImpressoraDTO resultado = impressoraService.buscarPorPatrimonio(patrimonio);

        // Assert (Verificação)
        assertNotNull(resultado);
        assertEquals(patrimonio, resultado.getPatrimonio()); 
        assertEquals(tipo, resultado.getTipo()); 
        
        verify(impressoraRepository).findByPatrimonio(patrimonio);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar por patrimônio de impressora inexistente")
    void deveLancarExcecao_QuandoBuscarPorPatrimonioInexistente() {
        // Arrange
        String patrimonio = "IMP_FALSA";
        when(impressoraRepository.findByPatrimonio(patrimonio)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            impressoraService.buscarPorPatrimonio(patrimonio);
        });
    }
}
