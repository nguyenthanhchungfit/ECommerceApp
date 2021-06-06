package com.ecommerce.model.data.mysql;

import java.util.List;

/**
 *
 * @author ngoclt2
 */
public class Order {

    private int userId;
    private int subTotal;
    private List<OrderItem> orderItems;

    public Order(int userId) {
        this.userId = userId;
    }

    public Order(int userId, int subTotal, List<OrderItem> orderItems) {
        this.userId = userId;
        this.subTotal = subTotal;
        this.orderItems = orderItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" + "userId=" + userId + ", subTotal=" + subTotal + ", orderItems=" + orderItems + '}';
    }

}
