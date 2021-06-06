/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 *
 * @author chungnt
 */
@Node("User")
@Getter
@Setter
public class NodeUser {

    @Id
    private int id;
    private String userName;

    @Relationship(type = "BUY")
    private Set<NodeProduct> boughtProducts;

    @Relationship(type = "VIEW")
    private Set<ViewRelationship> viewedProducts;

    public NodeUser() {
    }

    public NodeUser(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public NodeUser(int id, String userName, Set<NodeProduct> boughtProducts) {
        this.id = id;
        this.userName = userName;
        this.boughtProducts = boughtProducts;
    }

    public NodeUser(int id, String userName, boolean flag, Set<ViewRelationship> viewedProducts) {
        this.id = id;
        this.userName = userName;
        this.viewedProducts = viewedProducts;
    }

    public NodeUser(int id, String userName, Set<NodeProduct> boughtProducts, Set<ViewRelationship> viewedProducts) {
        this.id = id;
        this.userName = userName;
        this.boughtProducts = boughtProducts;
        this.viewedProducts = viewedProducts;
    }

    @Override
    public String toString() {
        return "NodeUser{" + "id=" + id + ", userName=" + userName + ", boughtProducts=" + boughtProducts + ", viewedProducts=" + viewedProducts + '}';
    }
}
