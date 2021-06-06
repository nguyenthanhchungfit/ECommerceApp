/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mongodb.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
@Getter
@Setter
public class User {

    @Id
    @Field(value = "id")
    private int id;

    @Field(value = "name")
    private String name;

    @Indexed(unique = true)
    @Field(value = "email")
    private String email;

    @Field(value = "date_of_birth")
    private Date dateOfBirth;

    @Indexed(unique = true)
    @Field(value = "account")
    private String account;

    @Field(value = "password")
    private String password;

    @Field(value = "address")
    private String address;

    public User(int id, String name, String email, Date dateOfBirth, String account, String password, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.account = account;
        this.password = password;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", dateOfBirth=" + dateOfBirth + ", account=" + account + ", password=" + password + ", address=" + address + '}';
    }
}
