/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import com.ecommerce.model.data.mongodb.entity.User;
import com.ecommerce.repository.mongodb.UserRepositoryCustom;
import com.ecommerce.repository.mongodb.UserRespository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;

    @Autowired
    private UserRespository userRepository;

    @RequestMapping("/user/insert")
    public String insertUser() {

        int id = this.userRepositoryCustom.getMaxUserId() + 1;
        return id + "";
    }

    @ResponseBody
    @RequestMapping("/users")
    public String getAllUsers() {

        List<User> users = this.userRepository.findAll();

        String html = "";
        for (User emp : users) {
            html += emp + "<br>";
        }

        return html;
    }

    @RequestMapping("/user")
    public User getUserById(@RequestParam(required = false) String id, @RequestParam(required = false) String email, @RequestParam(required = false) String password) {

        if (id != null) {
            return this.userRepository.findById(id);
        }
        if (email != null && password != null) {
            return this.userRepository.findByEmailAndPassword(email, password);
        }

        return null;
    }
}
