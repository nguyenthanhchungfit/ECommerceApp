/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 *
 * @author chungnt
 */
@Node("Brand")
@Getter
@Setter
public class NodeBrand {

    @Id
    private String name;

    @Relationship(type = "BELONG_TO", direction = Relationship.Direction.INCOMING)
    private List<NodeProduct> products;

    public NodeBrand() {
    }

    public NodeBrand(String name) {
        assert (StringUtils.isNotBlank(name));
        this.name = name;
//        this.id = this.name.hashCode();
    }

    // "id=" + id + 
    @Override
    public String toString() {
        return "NodeBrand{" + "name=" + name + ", products=" + products + '}';
    }
}
