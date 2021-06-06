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
public class ProductCategory {

    private Product product;
    private Category category;

    public ProductCategory() {
    }

    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductCategory{" + "product=" + product + ", category=" + category + '}';
    }

}
