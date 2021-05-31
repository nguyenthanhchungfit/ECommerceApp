/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.redis;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author chungnt
 */
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
    public UserSession getSessionById(String id);
}
