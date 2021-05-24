/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.entities;

import com.ecommerce.model.data.mysql.Product;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author chungnt
 */
@Getter
@Setter
public class ListProductResult {

    private int total;
    private boolean isMore;
    private int nItemsPerPage;
    private List<Product> items;

    public ListProductResult(int total, boolean isMore, int nItemsPerPage, List<Product> items) {
        this.total = total;
        this.isMore = isMore;
        this.nItemsPerPage = nItemsPerPage;
        this.items = items;
    }

}
