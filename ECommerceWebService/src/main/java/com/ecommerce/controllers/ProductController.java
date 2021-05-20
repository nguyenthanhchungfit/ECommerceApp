package com.ecommerce.controllers;

import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ngoclt2
 */
@RestController
public class ProductController {

    @CrossOrigin
    @GetMapping(value = "/products")
    public Map<String, Object> getProductById(@RequestParam Integer category, @RequestParam(required = false) Integer productId) {

        if (category != null && productId != null) {
            Product product = MySQLAdapter.INSTANCE.getProduct(category, productId);
            HashMap<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("data", product);
            return map;
        } else {
            List<Product> listProduct = MySQLAdapter.INSTANCE.getAllProduct();
            HashMap<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("data", listProduct);
            return map;
        }
    }

    public static void main(String[] args) {
        Product product = MySQLAdapter.INSTANCE.getProduct(8095, 50247928);
        System.out.println("product = " + product);
    }
}
