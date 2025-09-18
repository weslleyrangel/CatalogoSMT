package com.example.Catalogo;

import com.example.Catalogo.dto.ComputadorDTO;
import com.example.Catalogo.dto.ImpressoraDTO;
import com.example.Catalogo.service.interfaces.IAuthenticationService;
import com.example.Catalogo.service.interfaces.IComputadorService;
import com.example.Catalogo.service.interfaces.IImpressoraService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;

@SpringBootApplication
public class CatalogoSmtApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CatalogoSmtApplication.class, args);
        
        executarTestesIntegracao(context);
        
        exibirInformacoesSistema();
    }

    /**
     * Executa testes de integração para validar funcionamento do sistema
     */
    private static void executarTestesIntegracao(ApplicationContext context) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("    EXECUTANDO TESTES DE INTEGRAÇÃO");
        System.out.println("=".repeat(80));

        try {
            
            IAuthenticationService authService = context.getBean(IAuthenticationService.class);
            IComputadorService computadorService = context.getBean(IComputadorService.class);
            IImpressoraService impressoraService = context.getBean(IImpressoraService.class);

            // Executar testes
            testarAutenticacao(authService);
            testarServicosComputador(computadorService);
            testarServicosImpressora(impressoraService);
            testarIntegracaoCompleta(computadorService, impressoraService);

            System.out.println("\n✅ TODOS OS TESTES PASSARAM COM SUCESSO!");
            
        } catch (BeansException e) {
            System.err.println("\n❌ ERRO NOS TESTES: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=".repeat(80));
    }

    private static void testarAutenticacao(IAuthenticationService authService) {
        System.out.println("\n🔐 Testando Autenticação...");
        
        // Teste 1: Login válido
        var resultValido = authService.authenticate("admin@exemplo.com", "admin123");
        assert resultValido.isSuccess() : "Login válido deve funcionar";
        assert "Administrador".equals(resultValido.getUsername()) : "Nome de usuário incorreto";
        System.out.println("  ✓ Login válido funcionou");

        // Teste 2: Login inválido
        var resultInvalido = authService.authenticate("admin@exemplo.com", "senhaerrada");
        assert !resultInvalido.isSuccess() : "Login inválido não deve funcionar";
        System.out.println("  ✓ Login inválido rejeitado");

        // Teste 3: Validação de email vazio
        var resultEmailVazio = authService.authenticate("", "senha");
        assert !resultEmailVazio.isSuccess() : "Email vazio deve ser rejeitado";
        System.out.println("  ✓ Validação de email vazio funcionou");

        // Teste 4: Recuperação de senha
        var resultRecuperacao = authService.recoverPassword("admin@exemplo.com");
        assert resultRecuperacao.isSuccess() : "Recuperação de senha deve funcionar";
        System.out.println("  ✓ Recuperação de senha funcionou");
    }

    private static void testarServicosComputador(IComputadorService computadorService) {
        System.out.println("\n💻 Testando Serviços de Computador...");
        
        // Teste 1: Listar todos
        List<ComputadorDTO> computadores = computadorService.listarTodos();
        assert !computadores.isEmpty() : "Deve haver computadores carregados";
        System.out.println("  ✓ Listagem de computadores funcionou (" + computadores.size() + " encontrados)");

        // Teste 2: Buscar por patrimônio existente
        ComputadorDTO computador = computadorService.buscarPorPatrimonio("001TI2024");
        assert computador != null : "Deve encontrar o computador";
        assert "João Silva".equals(computador.getUsuario()) : "Usuário deve ser João Silva";
        System.out.println("  ✓ Busca por patrimônio funcionou");

        // Teste 3: Filtrar por setor
        Map<String, String> filtros = Map.of("setor", "TI");
        List<ComputadorDTO> computadoresTI = computadorService.filtrar(null, filtros);
        assert !computadoresTI.isEmpty() : "Deve haver computadores do setor TI";
        System.out.println("  ✓ Filtro por setor funcionou (" + computadoresTI.size() + " encontrados)");

        // Teste 4: Obter estatísticas
        Map<String, Long> estatisticas = computadorService.obterEstatisticas();
        assert estatisticas.containsKey("total") : "Estatísticas devem conter total";
        assert estatisticas.get("total") > 0 : "Total deve ser maior que zero";
        System.out.println("  ✓ Estatísticas funcionaram (total: " + estatisticas.get("total") + ")");

        // Teste 5: Computadores em manutenção
        List<ComputadorDTO> emManutencao = computadorService.obterEmManutencao();
        System.out.println("  ✓ Busca por computadores em manutenção funcionou (" + emManutencao.size() + " encontrados)");
    }

    private static void testarServicosImpressora(IImpressoraService impressoraService) {
        System.out.println("\n🖨️ Testando Serviços de Impressora...");
        
        // Teste 1: Listar todas
        List<ImpressoraDTO> impressoras = impressoraService.listarTodas();
        assert !impressoras.isEmpty() : "Deve haver impressoras carregadas";
        System.out.println("  ✓ Listagem de impressoras funcionou (" + impressoras.size() + " encontradas)");

        // Teste 2: Buscar por patrimônio
        ImpressoraDTO impressora = impressoraService.buscarPorPatrimonio("IMP001");
        assert impressora != null : "Deve encontrar a impressora";
        assert "Multifuncional".equals(impressora.getTipo()) : "Tipo deve ser Multifuncional";
        System.out.println("  ✓ Busca por patrimônio funcionou");

        // Teste 3: Buscar por tipo
        List<ImpressoraDTO> multifuncionais = impressoraService.buscarPorTipo("Multifuncional");
        assert !multifuncionais.isEmpty() : "Deve haver impressoras multifuncionais";
        System.out.println("  ✓ Busca por tipo funcionou (" + multifuncionais.size() + " encontradas)");

        // Teste 4: Buscar por status
        List<ImpressoraDTO> ativas = impressoraService.buscarPorStatus("Ativo");
        assert !ativas.isEmpty() : "Deve haver impressoras ativas";
        System.out.println("  ✓ Busca por status funcionou (" + ativas.size() + " ativas)");

        // Teste 5: Obter estatísticas
        Map<String, Long> estatisticas = impressoraService.obterEstatisticas();
        assert estatisticas.containsKey("total") : "Estatísticas devem conter total";
        assert estatisticas.get("total") > 0 : "Total deve ser maior que zero";
        System.out.println("  ✓ Estatísticas funcionaram (total: " + estatisticas.get("total") + ")");
    }

    private static void testarIntegracaoCompleta(IComputadorService computadorService, 
                                                IImpressoraService impressoraService) {
        System.out.println("\n🔄 Testando Integração Completa...");
        
        // Teste 1: Contagem total de ativos
        long totalComputadores = computadorService.obterEstatisticas().get("total");
        long totalImpressoras = impressoraService.obterEstatisticas().get("total");
        long totalAtivos = totalComputadores + totalImpressoras;
        
        assert totalAtivos > 0 : "Deve haver ativos no sistema";
        System.out.println("  ✓ Sistema possui " + totalAtivos + " ativos (" + 
                          totalComputadores + " computadores + " + totalImpressoras + " impressoras)");

        // Teste 2: Validar dados consistentes
        Map<String, Long> estatComputadores = computadorService.obterEstatisticas();
        long ativosComputadores = estatComputadores.get("ativos");
        long inativosComputadores = estatComputadores.get("inativos");
        long manutencaoComputadores = estatComputadores.get("manutencao");
        
        assert (ativosComputadores + inativosComputadores + manutencaoComputadores) == totalComputadores 
            : "Soma dos status deve ser igual ao total";
        System.out.println("  ✓ Consistência de dados validada");

        // Teste 3: Funcionalidade de filtros
        Map<String, String> filtroStatus = Map.of("status", "Ativo");
        List<ComputadorDTO> computadoresAtivos = computadorService.filtrar(null, filtroStatus);
        
        assert computadoresAtivos.size() == ativosComputadores : "Filtro deve retornar quantidade correta";
        System.out.println("  ✓ Consistência de filtros validada");
    }

    private static void exibirInformacoesSistema() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("    SISTEMA DE INVENTÁRIO DE TI - CATÁLOGO SMT");
        System.out.println("=".repeat(80));
        System.out.println("Aplicação iniciada com sucesso!");
        System.out.println();
        System.out.println("🌐 API Base URL: http://localhost:8080/api");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("   - JDBC URL: jdbc:h2:mem:inventory_db");
        System.out.println("   - Username: sa");
        System.out.println("   - Password: (em branco)");
        System.out.println();
        System.out.println("📋 Endpoints principais:");
        System.out.println("   - GET  /api/computadores          - Listar computadores");
        System.out.println("   - POST /api/computadores          - Criar computador");
        System.out.println("   - GET  /api/impressoras           - Listar impressoras");
        System.out.println("   - POST /api/impressoras           - Criar impressora");
        System.out.println("   - POST /api/auth/login            - Fazer login");
        System.out.println("   - GET  /api/computadores/manutencao - Listar em manutenção");
        System.out.println();
        System.out.println("🔐 Credenciais de teste:");
        System.out.println("   - admin@exemplo.com / admin123");
        System.out.println("   - user@exemplo.com / user123");
        System.out.println("   - ti@secretaria.gov.br / ti2024");
        System.out.println();
        System.out.println("🏗️  Arquitetura aplicada:");
        System.out.println("   ✓ Princípios SOLID implementados");
        System.out.println("   ✓ Repository Pattern");
        System.out.println("   ✓ Service Layer Pattern");
        System.out.println("   ✓ DTO Pattern");
        System.out.println("   ✓ Dependency Injection");
        System.out.println("   ✓ Exception Handling");
        System.out.println("=".repeat(80));
    }
}