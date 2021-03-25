/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.mysql.repo;

import com.ecommerce.model.mysql.entities.Order;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author chungnt
 */
public interface OrderRepository extends CrudRepository<Order, Long> {

    Order findById(long orderId);
}
