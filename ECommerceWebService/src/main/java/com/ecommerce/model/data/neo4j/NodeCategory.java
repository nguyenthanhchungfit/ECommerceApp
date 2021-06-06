/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

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
@Node("Category")
@Getter
@Setter
public class NodeCategory {

    @Id
    private int categoryId;

    private String name;

    @Relationship(type = "BELONG_TO", direction = Relationship.Direction.INCOMING)
    private List<NodeProduct> products;

    public NodeCategory() {
    }

    public NodeCategory(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public NodeCategory(String name, List<NodeProduct> products) {
        this.name = name;
        this.products = products;
    }

    @Override
    public String toString() {
        return "NodeCategory{" + "categoryId=" + categoryId + ", name=" + name + ", products=" + products + '}';
    }
}
