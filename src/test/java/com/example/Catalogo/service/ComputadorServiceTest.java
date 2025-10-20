package com.example.Catalogo.service;

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.exception.DuplicateResourceException;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.model.Computador;
import com.example.Catalogo.repository.ComputadorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ComputadorServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ComputadorServiceTest.class);

    @Mock
    private ComputadorRepository computadorRepository;

    @InjectMocks
    private ComputadorService computadorService;

    @Test
    @DisplayName("Deve retornar ComputadorDTO ao buscar por patrimônio existente")
    void deveRetornarComputadorDTO_QuandoBuscarPorPatrimonioExistente() {
        String patrimonio = "001TI2024";
        Computador computador = new Computador(patrimonio, "Ativo", "João Silva", "TI");
        when(computadorRepository.findByPatrimonio(patrimonio)).thenReturn(Optional.of(computador));

        try {
            ComputadorDTO resultado = computadorService.buscarPorPatrimonio(patrimonio);
            logger.info("Computador encontrado: {}", resultado);
            assertNotNull(resultado);
            assertEquals(patrimonio, resultado.getPatrimonio());
            verify(computadorRepository).findByPatrimonio(patrimonio);
        } catch (Exception e) {
            logger.error("Erro ao buscar computador por patrimônio", e);
            fail("Erro inesperado: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao buscar por patrimônio inexistente")
    void deveLancarExcecao_QuandoBuscarPorPatrimonioInexistente() {
        String patrimonio = "PATRIMONIO_FALSO";
        when(computadorRepository.findByPatrimonio(patrimonio)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            computadorService.buscarPorPatrimonio(patrimonio);
        });
        logger.info("Exceção esperada lançada: {}", exception.getMessage());
    }

    @Test
    @DisplayName("Deve adicionar um novo computador com sucesso")
    void deveAdicionarNovoComputador_ComSucesso() {
        ComputadorDTO novoComputadorDTO = new ComputadorDTO();
        novoComputadorDTO.setPatrimonio("002TI2025");
        novoComputadorDTO.setUsuario("Maria");

        when(computadorRepository.existsByPatrimonio(novoComputadorDTO.getPatrimonio())).thenReturn(false);
        when(computadorRepository.save(any(Computador.class))).thenAnswer(invocation -> invocation.getArgument(0));

        try {
            ComputadorDTO resultado = computadorService.adicionar(novoComputadorDTO);
            logger.info("Computador adicionado: {}", resultado);
            assertNotNull(resultado);
            assertEquals("002TI2025", resultado.getPatrimonio());
            verify(computadorRepository).save(any(Computador.class));
        } catch (Exception e) {
            logger.error("Erro ao adicionar computador", e);
            fail("Erro inesperado: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Deve lançar DuplicateResourceException ao tentar adicionar computador com patrimônio existente")
    void deveLancarExcecao_QuandoAdicionarComputadorComPatrimonioDuplicado() {
        ComputadorDTO computadorExistenteDTO = new ComputadorDTO();
        computadorExistenteDTO.setPatrimonio("001TI2024");
        when(computadorRepository.existsByPatrimonio(computadorExistenteDTO.getPatrimonio())).thenReturn(true);

        Exception exception = assertThrows(DuplicateResourceException.class, () -> {
            computadorService.adicionar(computadorExistenteDTO);
        });
        logger.info("Exceção esperada lançada: {}", exception.getMessage());
        verify(computadorRepository, never()).save(any(Computador.class));
    }
}