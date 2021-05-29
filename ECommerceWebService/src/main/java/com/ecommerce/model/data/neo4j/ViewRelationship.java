/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 *
 * @author chungnt
 */
@RelationshipProperties
@Getter
@Setter
public class ViewRelationship {

    @Id
    private String id;

    private int countViews;

    @TargetNode
    private NodeProduct product;

    public ViewRelationship() {
    }

    public ViewRelationship(String id, int countViews, NodeProduct product) {
        this.id = id;
        this.countViews = countViews;
        this.product = product;
    }
}
