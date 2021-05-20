/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.redis;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 *
 * @author chungnt
 */
@Entity
@Getter
@Setter
@RedisHash("user-session")
public class UserSession {

    @Id
    private String id;

    private long createTime;
    private long expiredTime;
    private String userId;

    public UserSession() {
    }
    
    public UserSession(String id, long createTime, long expiredTime, String userId) {
        this.id = id;
        this.createTime = createTime;
        this.expiredTime = expiredTime;
        this.userId = userId;
    }

    public UserSession(long createTime, long expiredTime, String userId) {
        this.createTime = createTime;
        this.expiredTime = expiredTime;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserSession{" + "id=" + id + ", createTime=" + createTime + ", expiredTime=" + expiredTime + ", userId=" + userId + '}';
    }


}
