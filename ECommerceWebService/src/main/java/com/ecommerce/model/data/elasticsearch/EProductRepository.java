/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @author chungnt
 */
public interface EProductRepository extends ElasticsearchRepository<EProduct, Long> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"app.product.id\": 104632}}]}}")
    public EProduct getProduct(long id);
}
