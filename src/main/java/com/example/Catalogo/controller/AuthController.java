package com.example.Catalogo.controller;

import com.example.Catalogo.service.AuthenticationService.AuthenticationResult; // Note que ele importa a classe interna de AuthenticationService
import com.example.Catalogo.service.interfaces.IAuthenticationService; // Importa a interface
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final IAuthenticationService authenticationService; 

    @Autowired
    public AuthController(IAuthenticationService authenticationService) { 
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint para realizar login no sistema.
     * POST /api/auth/login
     * @param loginRequest
     * @return 
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResult result = authenticationService.authenticate(
            loginRequest.getEmail(),
            loginRequest.getSenha()
        );

        LoginResponse response = new LoginResponse(
            result.isSuccess(),
            result.getMessage(),
            result.getUsername(),
            result.getRole()
        );

        return result.isSuccess() 
            ? ResponseEntity.ok(response)
            : ResponseEntity.status(401).body(response);
    }

    /**
     * Endpoint para realizar logout do sistema.
     * POST /api/auth/logout
     * @return 
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", true);
        response.put("mensagem", "Logout realizado com sucesso");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para recuperação de senha.
     * POST /api/auth/recuperar-senha
     * @param request
     * @return 
     */
    @PostMapping("/recuperar-senha")
    public ResponseEntity<Map<String, Object>> recuperarSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        AuthenticationResult result = authenticationService.recoverPassword(email);

        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", result.isSuccess());
        response.put("mensagem", result.getMessage());

        return result.isSuccess() 
            ? ResponseEntity.ok(response)
            : ResponseEntity.badRequest().body(response);
    }

    /**
     * Endpoint para verificar se o usuário está autenticado.
     * GET /api/auth/verificar
     * @param authHeader
     * @return 
     */
    @GetMapping("/verificar")
    public ResponseEntity<Map<String, Object>> verificarAutenticacao(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        Map<String, Object> response = new HashMap<>();
        boolean isAuthenticated = authenticationService.isValidSession(authHeader);

        response.put("autenticado", isAuthenticated);
        if (isAuthenticated) {
            response.put("usuario", "Usuário Simulado");
        }

        return ResponseEntity.ok(response);
    }

    public static class LoginRequest {
        private String email;
        private String senha;

        public LoginRequest() {}

        public LoginRequest(String email, String senha) {
            this.email = email;
            this.senha = senha;
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }

    public static class LoginResponse {
        private boolean sucesso;
        private String mensagem;
        private String usuario;
        private String perfil;

        public LoginResponse() {}

        public LoginResponse(boolean sucesso, String mensagem, String usuario, String perfil) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
            this.usuario = usuario;
            this.perfil = perfil;
        }

        public boolean isSucesso() { return sucesso; }
        public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }
        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }
        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }
        public String getPerfil() { return perfil; }
        public void setPerfil(String perfil) { this.perfil = perfil; }
    }
}