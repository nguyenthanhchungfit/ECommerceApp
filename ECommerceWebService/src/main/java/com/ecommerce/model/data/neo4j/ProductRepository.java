/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author chungnt
 */
public interface ProductRepository extends Neo4jRepository<NodeProduct, Long> {

    @Query("MATCH (u:User {id:$userId})-[r:VIEW]->(p:Product)-[:BELONG_TO]->(c:Category)\n"
        + "WITH sum(r.countViews) as count, c.categoryId as cId\n"
        + "ORDER BY count desc\n"
        + "LIMIT 1\n"
        + "MATCH(p1:Product)-[:BELONG_TO]->(c1:Category {categoryId: cId})\n"
        + "return p1\n"
        + "ORDER BY p1.rating DESC\n"
        + "LIMIT $nItems")
    public List<NodeProduct> getListRecommenedProducts(@Param("userId") int userId, @Param("nItems") int nItems);
}
