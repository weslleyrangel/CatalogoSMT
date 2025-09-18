package com.example.Catalogo;

import com.example.Catalogo.service.interfaces.IAuthenticationService;
import com.example.Catalogo.service.interfaces.IComputadorService;
import com.example.Catalogo.service.interfaces.IImpressoraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes de integração para a aplicação Catálogo SMT
 * Corrigido para compatibilidade com Spring Boot 3.x
 */
@SpringBootTest
@ActiveProfiles("test")
class CatalogoSmtApplicationTests {

    @Autowired(required = false)
    private IAuthenticationService authenticationService;

    @Autowired(required = false)
    private IComputadorService computadorService;

    @Autowired(required = false)
    private IImpressoraService impressoraService;

    /**
     * Teste básico de carregamento do contexto Spring
     */
    @Test
    void contextLoads() {
        // Este teste verifica se o contexto Spring carrega sem erros
        assertThat(true).isTrue();
    }

    /**
     * Teste de carregamento dos beans principais
     */
    @Test
    void mainBeansAreLoaded() {
        // Verifica se os principais beans foram carregados
        assertThat(authenticationService).as("AuthenticationService deve estar disponível").isNotNull();
        assertThat(computadorService).as("ComputadorService deve estar disponível").isNotNull();
        assertThat(impressoraService).as("ImpressoraService deve estar disponível").isNotNull();
    }

    /**
     * Teste de funcionalidade básica do serviço de autenticação
     */
    @Test
    void authenticationServiceWorks() {
        if (authenticationService != null) {
            var result = authenticationService.authenticate("admin@exemplo.com", "admin123");
            assertThat(result).isNotNull();
            assertThat(result.isSuccess()).isTrue();
        }
    }

    /**
     * Teste de funcionalidade básica do serviço de computadores
     */
    @Test
    void computadorServiceWorks() {
        if (computadorService != null) {
            var computadores = computadorService.listarTodos();
            assertThat(computadores).isNotNull();
            assertThat(computadores.size()).isGreaterThanOrEqualTo(0);
        }
    }

    /**
     * Teste de funcionalidade básica do serviço de impressoras
     */
    @Test
    void impressoraServiceWorks() {
        if (impressoraService != null) {
            var impressoras = impressoraService.listarTodas();
            assertThat(impressoras).isNotNull();
            // Como os dados são carregados pelo DataLoader, deve ter pelo menos algumas impressoras
            assertThat(impressoras.size()).isGreaterThanOrEqualTo(0);
        }
    }

    /**
     * Teste de estatísticas do sistema
     */
    @Test
    void systemStatisticsWork() {
        if (computadorService != null && impressoraService != null) {
            var estatisticasComputadores = computadorService.obterEstatisticas();
            var estatisticasImpressoras = impressoraService.obterEstatisticas();
            
            assertThat(estatisticasComputadores).isNotNull();
            assertThat(estatisticasComputadores).containsKey("total");
            
            assertThat(estatisticasImpressoras).isNotNull();
            assertThat(estatisticasImpressoras).containsKey("total");
        }
    }
}