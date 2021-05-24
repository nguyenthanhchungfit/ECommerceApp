/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import java.util.List;
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
@Node
@Getter
@Setter
public class NodeUser {

    @Id
    private String _id;
    private String userName;

    @Relationship(type = "BUY")
    private List<NodeProduct> products;

    public NodeUser() {
    }

    public NodeUser(String _id, String userName, List<NodeProduct> products) {
        this._id = _id;
        this.userName = userName;
        this.products = products;
    }

}
