/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 *
 * @author chungnt
 */
//@Configuration
public class RedisClusterCfg {

//    @Autowired
//    ClusterConfigurationProperties clusterProperties;
//
//    public @Bean
//    RedisConnectionFactory connectionFactory() {
//
//        return new JedisConnectionFactory(
//            new RedisClusterConfiguration(clusterProperties.getNodes()));
//    }
}
