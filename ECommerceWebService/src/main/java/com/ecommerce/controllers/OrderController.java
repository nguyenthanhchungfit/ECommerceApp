package com.ecommerce.controllers;

import com.ecommerce.model.data.mysql.Order;
import com.ecommerce.model.data.mysql.OrderItem;
import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ngoclt2
 */
@CrossOrigin
@RestController
public class OrderController {

    @PostMapping(value = "/order/add")
    public Order addOrder(@RequestBody Order order) {
        if (order == null) {
            return null;
        }
        int orderId = MySQLAdapter.INSTANCE.countOrder();
        if (orderId >= 0) {
            System.out.println("subTotal = " + orderId);
            int currOrderId = orderId + 1;
            int userId = order.getUserId();
            int subTotal = order.getSubTotal();
            System.out.println("subTotal = " + currOrderId);
            boolean success = MySQLAdapter.INSTANCE.insertOrder(currOrderId, userId, subTotal);
            if (success) {
                List<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem item : orderItems) {
                    int productId = item.getProductId();
                    int category = item.getCategory();
                    int quantity = item.getQuantity();
                    int price = item.getPrice();
                    System.out.println("insert detail:  = " + currOrderId + "\t" + productId + "\t" + category + "\t" + quantity + "\t" + price);
                    boolean insertOrderDetail = MySQLAdapter.INSTANCE.insertOrderDetail(currOrderId, productId, category, quantity, price);
                }
            }

        }
        return null;
    }

    @GetMapping(value = "/order/detail")
    public List<Map<String, Object>> addOrder(@RequestParam Integer userId) {
        if (userId == null) {
            return null;
        }
        List<Integer> orderIds = MySQLAdapter.INSTANCE.getAllOrderIdByUserId(userId);
        if (orderIds == null || orderIds.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> ret = new ArrayList<>();

        for (Integer orderId : orderIds) {
            List<OrderItem> orderItems = MySQLAdapter.INSTANCE.getAllOrderItemByOrderId(orderId);
            if (orderItems != null && !orderItems.isEmpty()) {
                for (OrderItem orderItem : orderItems) {

                    Product product = MySQLAdapter.INSTANCE.getProduct(orderItem.getCategory(), orderItem.getProductId());
                    if (product != null) {
                        Map<String, Object> obj = new HashMap<>();
                        obj.put("id", product.getId() + "");
                        obj.put("name", product.getName());
                        obj.put("thumbUrl", product.getThumbUrl());
                        obj.put("price", orderItem.getPrice());
                        ret.add(obj);
                    }
                }
            }
        }
        return ret;
    }
}
