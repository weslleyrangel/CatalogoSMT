package com.example.Catalogo.service;

import com.example.Catalogo.service.interfaces.IAuthenticationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Serviço de autenticação responsável por validar credenciais e gerenciar sessões.
 * Implementa o princípio SRP separando a lógica de autenticação do controle de rotas HTTP.
 */
@Service 
public class AuthenticationService implements IAuthenticationService {

    private final Map<String, UserCredential> validCredentials;

    public AuthenticationService() {
        this.validCredentials = initializeCredentials();
    }

    @Override
    public AuthenticationResult authenticate(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            return AuthenticationResult.failure("Email é obrigatório");
        }

        if (password == null || password.trim().isEmpty()) {
            return AuthenticationResult.failure("Senha é obrigatória");
        }

        String normalizedEmail = email.toLowerCase().trim();
        UserCredential credential = validCredentials.get(normalizedEmail);

        if (credential != null && credential.getPassword().equals(password)) {
            return AuthenticationResult.success(
                credential.getUsername(),
                credential.getRole(),
                "Login realizado com sucesso"
            );
        }

    return AuthenticationResult.failure("Email ou senha inválidos");
    }

    @Override
    public boolean isValidSession(String authToken) {
        return authToken != null && !authToken.trim().isEmpty();
    }

    @Override
    public AuthenticationResult recoverPassword(String email) {
        if (email == null || email.trim().isEmpty()) {
            return AuthenticationResult.failure("Email é obrigatório");
        }

        return AuthenticationResult.success(
            null, null,
            "Se o email estiver cadastrado, você receberá as instruções de recuperação"
        );
    }

    private Map<String, UserCredential> initializeCredentials() {
        Map<String, UserCredential> credentials = new HashMap<>();
        credentials.put("admin@exemplo.com", new UserCredential("admin123", "Administrador", "ADMIN"));
        credentials.put("user@exemplo.com", new UserCredential("user123", "Usuário", "USER"));
        credentials.put("ti@secretaria.gov.br", new UserCredential("ti2024", "TI Manager", "ADMIN"));
        credentials.put("operador@secretaria.gov.br", new UserCredential("operador123", "Operador", "USER"));
        return credentials;
    }

    /**
     * Classe interna para representar credenciais de usuário
     */
    private static class UserCredential { 
        private final String password;
        private final String username;
        private final String role;

        public UserCredential(String password, String username, String role) {
            this.password = password;
            this.username = username;
            this.role = role;
        }

        public String getPassword() { return password; }
        public String getUsername() { return username; }
        public String getRole() { return role; }
    }

    /**
     * Classe para representar o resultado da autenticação
     */
    public static class AuthenticationResult { 
        private final boolean success;
        private final String message;
        private final String username;
        private final String role;

        private AuthenticationResult(boolean success, String message, String username, String role) {
            this.success = success;
            this.message = message;
            this.username = username;
            this.role = role;
        }

        public static AuthenticationResult success(String username, String role, String message) {
            return new AuthenticationResult(true, message, username, role);
        }

        public static AuthenticationResult failure(String message) {
            return new AuthenticationResult(false, message, null, null);
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String getUsername() { return username; }
        public String getRole() { return role; }
    }
}
