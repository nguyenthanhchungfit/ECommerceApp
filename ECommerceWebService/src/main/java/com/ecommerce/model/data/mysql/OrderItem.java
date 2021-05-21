package com.ecommerce.model.data.mysql;

/**
 *
 * @author ngoclt2
 */
public class OrderItem {

    private int orderId;
    private int productId;
    private int category;
    private int quantity;
    private int price;

    public OrderItem() {
    }

    public OrderItem(int id, int productId, int category, int quantity, int price) {
        this.orderId = id;
        this.productId = productId;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" + "id=" + orderId + ", category=" + category + ", quantity=" + quantity + ", price=" + price + '}';
    }
}
