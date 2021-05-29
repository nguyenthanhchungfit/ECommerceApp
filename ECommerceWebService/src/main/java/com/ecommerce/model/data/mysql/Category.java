/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mysql;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author chungnt
 */
@Getter
@Setter
public class Category {

    private int categoryId;
    private String categoryName;

    public Category() {
    }

    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + '}';
    }
}
