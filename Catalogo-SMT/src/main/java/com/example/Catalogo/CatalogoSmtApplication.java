package com.example.Catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoSmtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogoSmtApplication.class, args);
        
        System.out.println("\n" +
            "=================================================================\n" +
            "    Sistema de Inventário de TI - Catálogo SMT                   \n" +
            "=================================================================\n" +
            "Aplicação iniciada com sucesso!\n" +
            "\n" +
            "🌐 API Base URL: http://localhost:8080/api\n" +
            "🗄️  H2 Console: http://localhost:8080/h2-console\n" +
            "   - JDBC URL: jdbc:h2:mem:inventory_db\n" +
            "   - Username: sa\n" +
            "   - Password: (em branco)\n" +
            "\n" +
            "📋 Endpoints principais:\n" +
            "   - GET  /api/computadores          - Listar computadores\n" +
            "   - POST /api/computadores          - Criar computador\n" +
            "   - GET  /api/impressoras           - Listar impressoras\n" +
            "   - POST /api/impressoras           - Criar impressora\n" +
            "   - POST /api/auth/login            - Fazer login\n" +
            "   - GET  /api/computadores/manutencao - Listar em manutenção\n" +
            "\n" +
            "🔐 Credenciais de teste:\n" +
            "   - admin@exemplo.com / admin123\n" +
            "   - user@exemplo.com / user123\n" +
            "   - ti@secretaria.gov.br / ti2024\n" +
            "=================================================================\n"
        );
    }
}
