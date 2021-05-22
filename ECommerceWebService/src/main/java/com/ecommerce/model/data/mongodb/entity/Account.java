/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mongodb.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

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

    private String username;
    private String password;
    private String userId;

    public Account() {
    }

    public Account(String id, String username, String password, String userId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

}
