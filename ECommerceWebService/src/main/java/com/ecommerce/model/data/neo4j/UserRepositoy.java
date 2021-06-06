/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author chungnt
 */
public interface UserRepositoy extends Neo4jRepository<NodeUser, Integer> {

    @Query("MATCH (u:User {id:$userId}), (p:Product {productId:$productId})\n"
        + "MERGE (u)-[r:VIEW]->(p)\n"
        + "ON CREATE SET r.countViews = 1\n, r.id=$userId + '-'+ $productId\n"
        + "ON MATCH SET r.countViews = r.countViews + 1\n"
        + "RETURN u")
    public NodeUser viewProduct(@Param("userId") int userId, @Param("productId") long productId);
    
    public NodeUser getUserById(int id);
}
