/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mongodb;

import java.util.List;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String name;
    private String phoneNumber;
    private String email;
    private boolean gender;
    private String dob;
    private String address;
    private List<ReceivedAddress> receivedAddresses;

    public User() {
    }

    public User(String name, String phoneNumber, String email, boolean gender, String dob, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
    }

    public User(String name, String phoneNumber, String email, boolean gender, String dob, String address, List<ReceivedAddress> receivedAddresses) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.receivedAddresses = receivedAddresses;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + ", gender=" + gender + ", dob=" + dob + ", address=" + address + ", receivedAddresses=" + receivedAddresses + '}';
    }
    
    
}
