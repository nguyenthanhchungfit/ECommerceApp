package com.ecommerce.controllers;

import com.ecommerce.common.ErrorDefinition;
import com.ecommerce.entities.RestResponseEntity;
import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ngoclt2
 */
@RestController
public class ProductController {

    @CrossOrigin
    @GetMapping(value = "/api/products")
    public RestResponseEntity getProductById(@RequestParam(name = "category", required = false) Integer categoryId,
        @RequestParam(name = "page", defaultValue = "0") int page) {

        if (page < 0) {
            page = 0;
        }
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        List<Product> listProduct = null;
        if (categoryId != null) {
            listProduct = MySQLAdapter.INSTANCE.getListProductsByCategoryId(categoryId, page);
        } else {
            listProduct = MySQLAdapter.INSTANCE.getAllProduct(page);
        }
        data = listProduct;
        return new RestResponseEntity(error, data);
    }

    @CrossOrigin
    @GetMapping("/api/products/{productId}")
    public RestResponseEntity getDetailProduct(@PathVariable long productId) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        if (productId > 0) {
            Product product = MySQLAdapter.INSTANCE.getProductById(productId);
            data = product;
        } else {
            error = ErrorDefinition.ERR_INVALID_PARAM;
        }
        return new RestResponseEntity(error, data);
    }

    public static void main(String[] args) {
        Product product = MySQLAdapter.INSTANCE.getProduct(8095, 50247928);
        System.out.println("product = " + product);
    }
}
