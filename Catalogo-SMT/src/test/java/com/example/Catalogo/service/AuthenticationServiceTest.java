package com.example.Catalogo.service;

import com.example.Catalogo.service.AuthenticationService.AuthenticationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários essenciais para AuthenticationService
 * Focado nas validações básicas de login
 */
class AuthenticationServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceTest.class);

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService();
    }

    @Test
    void authenticate_ComCredenciaisValidas_DeveRetornarSucesso() {
        AuthenticationResult resultado = authenticationService.authenticate(
                "admin@exemplo.com", "admin123");
        logger.info("Resultado autenticação válida: {}", resultado);
        assertTrue(resultado.isSuccess());
        assertEquals("Administrador", resultado.getUsername());
        assertEquals("ADMIN", resultado.getRole());
    }

    @Test
    void authenticate_ComCredenciaisInvalidas_DeveRetornarFalha() {
        AuthenticationResult resultado = authenticationService.authenticate(
                "admin@exemplo.com", "senhaErrada");
        logger.info("Resultado autenticação inválida: {}", resultado);
        assertFalse(resultado.isSuccess());
        assertEquals("Email ou senha inválidos", resultado.getMessage());
    }

    @Test
    void authenticate_ComEmailVazio_DeveRetornarErroValidacao() {
        AuthenticationResult resultado = authenticationService.authenticate("", "admin123");
        logger.info("Resultado autenticação com email vazio: {}", resultado);
        assertFalse(resultado.isSuccess());
        assertEquals("Email é obrigatório", resultado.getMessage());
    }

    @Test
    void authenticate_ComSenhaVazia_DeveRetornarErroValidacao() {
        AuthenticationResult resultado = authenticationService.authenticate(
                "admin@exemplo.com", "");
        logger.info("Resultado autenticação com senha vazia: {}", resultado);
        assertFalse(resultado.isSuccess());
        assertEquals("Senha é obrigatória", resultado.getMessage());
    }

    @Test
    void isValidSession_ComTokenValido_DeveRetornarTrue() {
        boolean resultado = authenticationService.isValidSession("token-valido-123");
        logger.info("Resultado sessão válida: {}", resultado);
        assertTrue(resultado);
    }

    @Test
    void isValidSession_ComTokenNulo_DeveRetornarFalse() {
        boolean resultado = authenticationService.isValidSession(null);
        logger.info("Resultado sessão nula: {}", resultado);
        assertFalse(resultado);
    }
}