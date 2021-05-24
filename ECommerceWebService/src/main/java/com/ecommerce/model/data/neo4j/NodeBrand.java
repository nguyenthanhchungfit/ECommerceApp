/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 *
 * @author chungnt
 */
@Node
@Getter
@Setter
public class NodeBrand {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "BELONG_TO", direction = Relationship.Direction.INCOMING)
    private List<NodeProduct> products;
    
    public NodeBrand() {
    }

    public NodeBrand(String name) {
        this.name = name;
    }

    
    

}
