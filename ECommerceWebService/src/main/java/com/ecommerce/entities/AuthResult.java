/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.entities;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author chungnt
 */
@Getter
@Setter
public class AuthResult {

    private int id;
    private String password;

    public AuthResult() {
    }

    public AuthResult(int id, String password) {
        this.id = id;
        this.password = password;
    }
}
