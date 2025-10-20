package com.example.Catalogo;

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.exception.DuplicateResourceException;
import com.example.Catalogo.exception.ResourceNotFoundException;
import com.example.Catalogo.service.interfaces.IComputadorService;
import com.example.Catalogo.service.interfaces.IImpressoraService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CatalogoSmtApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(CatalogoSmtApplicationTests.class);

    @Autowired
    private IComputadorService computadorService;

    @Autowired
    private IImpressoraService impressoraService;

    @Test
    @DisplayName("Contexto Spring deve carregar com sucesso")
    void contextLoads() {
        logger.info("Iniciando teste: contextLoads");
        try {
            assertThat(computadorService).isNotNull();
            assertThat(impressoraService).isNotNull();
            logger.info("Serviços injetados com sucesso.");
        } catch (Exception e) {
            logger.error("Erro ao carregar contexto Spring", e);
            throw new AssertionError("Erro ao carregar contexto Spring: " + e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Serviço de Computador deve adicionar e buscar um novo registro")
    void computadorServiceShouldAddAndRetrieveData() {
        logger.info("Iniciando teste: computadorServiceShouldAddAndRetrieveData");
        ComputadorDTO novoComputador = new ComputadorDTO();
        novoComputador.setPatrimonio("INTEGRACAO-01");
        novoComputador.setUsuario("Teste de Integração");
        novoComputador.setSetor("TESTE");
        novoComputador.setStatus("Ativo");

        try {
            ComputadorDTO existente = null;
            try {
                existente = computadorService.buscarPorPatrimonio("INTEGRACAO-01");
                logger.info("Computador já existe: {}", existente);
            } catch (ResourceNotFoundException ignored) {
                logger.info("Computador não encontrado, será adicionado.");
            }

            if (existente == null) {
                computadorService.adicionar(novoComputador);
                logger.info("Computador adicionado: {}", novoComputador);
            }

            ComputadorDTO computadorSalvo = computadorService.buscarPorPatrimonio("INTEGRACAO-01");
            logger.info("Computador salvo encontrado: {}", computadorSalvo);
            assertThat(computadorSalvo).isNotNull();
            assertThat(computadorSalvo.getUsuario()).isEqualTo("Teste de Integração");
        } catch (DuplicateResourceException e) {
            logger.error("Falha ao adicionar: patrimônio duplicado", e);
            throw new AssertionError("Falha ao adicionar: patrimônio duplicado. Possível causa: teste rodando múltiplas vezes sem limpar o banco.", e);
        } catch (ResourceNotFoundException e) {
            logger.error("Falha ao buscar computador recém-adicionado", e);
            throw new AssertionError("Falha ao buscar computador recém-adicionado. Possível causa: erro ao salvar ou buscar.", e);
        } catch (Exception e) {
            logger.error("Erro inesperado", e);
            throw new AssertionError("Erro inesperado: " + e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Dados iniciais devem ser carregados ao iniciar a aplicação")
    void initialDataShouldBeLoaded() {
        logger.info("Iniciando teste: initialDataShouldBeLoaded");
        try {
            ComputadorDTO computador = computadorService.buscarPorPatrimonio("001TI2024");
            logger.info("Dados iniciais encontrados: {}", computador);
            assertThat(computador).isNotNull();
            assertThat(computador.getUsuario()).isEqualTo("João Silva");
        } catch (ResourceNotFoundException e) {
            logger.error("Falha ao encontrar patrimônio inicial", e);
            throw new AssertionError("Falha ao encontrar patrimônio inicial. Possível causa: DataLoader não executou ou dados iniciais ausentes.", e);
        } catch (Exception e) {
            logger.error("Erro inesperado", e);
            throw new AssertionError("Erro inesperado: " + e.getMessage(), e);
        }
    }
}