/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mongodb.repository;

import com.ecommerce.model.data.mongodb.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author chungnt
 */
public interface AccountRepository extends MongoRepository<Account, String> {

    public Account getAccountByUserName(String userName);
}
