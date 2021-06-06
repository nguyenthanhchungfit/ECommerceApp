/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 *
 * @author chungnt
 */
public interface BrandRepository extends Neo4jRepository<NodeBrand, String>{
    
}
