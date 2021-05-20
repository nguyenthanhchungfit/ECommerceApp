/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mongodb;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 *
 * @author chungnt
 */
@Entity
@Getter
@Setter
public class Account {

    @Id
    private String id;

    private String account;
    private String password;
    private String userId;

    public Account() {
    }

    public Account(String account, String password, String userId) {
        this.account = account;
        this.password = password;
        this.userId = userId;
    }

}
