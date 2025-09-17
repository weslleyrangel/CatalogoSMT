package com.example.Catalogo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    public static class LoginRequest {

        private String email;
        private String senha;

        public LoginRequest() {
        }

        public LoginRequest(String email, String senha) {
            this.email = email;
            this.senha = senha;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }

    public static class LoginResponse {

        private boolean sucesso;
        private String mensagem;
        private String usuario;
        private String perfil;

        public LoginResponse() {
        }

        public LoginResponse(boolean sucesso, String mensagem, String usuario, String perfil) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
            this.usuario = usuario;
            this.perfil = perfil;
        }

        public boolean isSucesso() {
            return sucesso;
        }

        public void setSucesso(boolean sucesso) {
            this.sucesso = sucesso;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getPerfil() {
            return perfil;
        }

        public void setPerfil(String perfil) {
            this.perfil = perfil;
        }
    }

    /**
     * @param loginRequest Dados de login (email e senha)
     *
     * @return Resposta de autenticação
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "Email é obrigatório", null, null));
        }

        if (loginRequest.getSenha() == null || loginRequest.getSenha().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "Senha é obrigatória", null, null));
        }

        Map<String, String[]> credenciaisValidas = new HashMap<>();
        credenciaisValidas.put("admin@exemplo.com", new String[]{"admin123", "Administrador", "ADMIN"});
        credenciaisValidas.put("user@exemplo.com", new String[]{"user123", "Usuário", "USER"});
        credenciaisValidas.put("ti@secretaria.gov.br", new String[]{"ti2024", "TI Manager", "ADMIN"});
        credenciaisValidas.put("operador@secretaria.gov.br", new String[]{"operador123", "Operador", "USER"});

        String email = loginRequest.getEmail().toLowerCase().trim();
        String senha = loginRequest.getSenha();

        if (credenciaisValidas.containsKey(email)) {
            String[] dadosUsuario = credenciaisValidas.get(email);
            String senhaCorreta = dadosUsuario[0];
            String nomeUsuario = dadosUsuario[1];
            String perfilUsuario = dadosUsuario[2];

            if (senhaCorreta.equals(senha)) {
                return ResponseEntity.ok(
                        new LoginResponse(true, "Login realizado com sucesso", nomeUsuario, perfilUsuario)
                );
            }
        }

        return ResponseEntity.status(401)
                .body(new LoginResponse(false, "Email ou senha inválidos", null, null));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", true);
        response.put("mensagem", "Logout realizado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<Map<String, Object>> recuperarSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.trim().isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("sucesso", false);
            response.put("mensagem", "Email é obrigatório");
            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("sucesso", true);
        response.put("mensagem", "Se o email estiver cadastrado, você receberá as instruções de recuperação");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verificar")
    public ResponseEntity<Map<String, Object>> verificarAutenticacao(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        Map<String, Object> response = new HashMap<>();

        if (authHeader != null && !authHeader.trim().isEmpty()) {
            response.put("autenticado", true);
            response.put("usuario", "Usuário Simulado");
        } else {
            response.put("autenticado", false);
        }

        return ResponseEntity.ok(response);
    }
}
