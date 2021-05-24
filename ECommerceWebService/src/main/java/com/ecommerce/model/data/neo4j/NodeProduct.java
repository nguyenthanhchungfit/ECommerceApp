/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import com.ecommerce.model.data.neo4j.NodeUser;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
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
public class NodeProduct {

    @Id
    private long productId;

    private double rating;

    @Relationship(type = "BUY", direction = Relationship.Direction.INCOMING)
    private List<NodeUser> users;

    @Relationship(type = "BELONG_TO")
    private NodeCategory category;

    @Relationship(type = "BELONG_TO")
    private NodeBrand brand;

    public NodeProduct() {
    }

    public NodeProduct(long productId, double rating, NodeCategory category, NodeBrand brand) {
        this.productId = productId;
        this.rating = rating;
        this.category = category;
        this.brand = brand;
    }
    
    
}
