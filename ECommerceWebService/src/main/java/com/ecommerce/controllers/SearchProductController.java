/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import com.ecommerce.common.Constant;
import com.ecommerce.common.ErrorDefinition;
import com.ecommerce.entities.ListProductResult;
import com.ecommerce.entities.RestResponseEntity;
import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class SearchProductController {

    @CrossOrigin
    @GetMapping
    public RestResponseEntity searchProducts(@RequestParam(name = "sseach") String ssearch,
        @RequestParam(name = "page", defaultValue = "0") int page) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        if (StringUtils.isNotBlank(ssearch)) {
            // search product
            int total = MySQLAdapter.INSTANCE.getTotalOfProductsByName(ssearch);
            List<Product> products = MySQLAdapter.INSTANCE.searchProducsByName(ssearch, page, Constant.NITEMS_PER_PAGE);
            boolean isMore = products != null && products.size() == Constant.NITEMS_PER_PAGE;
            data = new ListProductResult(total, isMore, Constant.NITEMS_PER_PAGE, products);
        }
        RestResponseEntity resp = new RestResponseEntity(error, data);
        return resp;
    }
}
