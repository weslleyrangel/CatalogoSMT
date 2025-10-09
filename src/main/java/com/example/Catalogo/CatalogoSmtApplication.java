package com.example.Catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CatalogoSmtApplication {

    public static void main(String[] args) {
        // A aplicação agora apenas inicia o contexto Spring e exibe as informações
        ApplicationContext context = SpringApplication.run(CatalogoSmtApplication.class, args);
        
        exibirInformacoesSistema();
    }

    /**
     * Exibe informações úteis sobre a aplicação no console ao iniciar.
     */
    private static void exibirInformacoesSistema() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("    SISTEMA DE INVENTÁRIO DE TI - CATÁLOGO SMT");
        System.out.println("=".repeat(80));
        System.out.println("Aplicação iniciada com sucesso!");
        System.out.println();
        System.out.println("🌐 API Base URL: http://localhost:8080/api");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("   - JDBC URL: jdbc:h2:mem:inventory_db");
        System.out.println("   - Username: natal");
        System.out.println("   - Password: rn");
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