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
public class RestResponseEntity {

    private int error;
    private Object data;

    public RestResponseEntity() {
    }

    public RestResponseEntity(int error, Object data) {
        this.error = error;
        this.data = data;
    }
}
