/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Catalogo.service.interfaces;

import com.example.Catalogo.service.AuthenticationService.AuthenticationResult;

/**
 *
 * @author wesll
 */
public interface IAuthenticationService {
    AuthenticationResult authenticate(String email, String password);
    boolean isValidSession(String authToken);
    AuthenticationResult recoverPassword(String email);
}
