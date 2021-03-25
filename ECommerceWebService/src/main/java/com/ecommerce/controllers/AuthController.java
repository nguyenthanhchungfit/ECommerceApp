/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import com.ecommerce.entities.AuthResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class AuthController {

    @GetMapping("/api/auth/login")
    public AuthResult login() {
        return null;
    }

    @GetMapping("/api/auth/logout")
    public AuthResult logout() {

        return null;
    }
}
