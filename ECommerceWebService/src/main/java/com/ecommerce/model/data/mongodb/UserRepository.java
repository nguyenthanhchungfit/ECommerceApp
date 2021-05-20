/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author chungnt
 */
@Service
public interface UserRepository extends MongoRepository<User, String> {

    public User findByPhoneNumber(String phoneNumber);

    public User findByEmail(String email);

}
