/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import com.ecommerce.common.ErrorDefinition;
import com.ecommerce.entities.RestResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class SearchProductController {

    @GetMapping
    public RestResponseEntity searchProducts(@RequestParam(name = "sseach") String ssearch) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        if (StringUtils.isNotBlank(ssearch)) {
            // search product
        }
        RestResponseEntity resp = new RestResponseEntity(error, data);
        return resp;
    }
}
