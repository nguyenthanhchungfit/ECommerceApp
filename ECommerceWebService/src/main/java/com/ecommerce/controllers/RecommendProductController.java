/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import com.ecommerce.common.Constant;
import com.ecommerce.common.ErrorDefinition;
import com.ecommerce.entities.RestResponseEntity;
import com.ecommerce.model.data.redis.UserSession;
import com.ecommerce.model.data.redis.UserSessionRepository;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class RecommendProductController {

    @Autowired
    private UserSessionRepository sessionRepo;

    @CrossOrigin
    @GetMapping("/api/recommend/product")
    public RestResponseEntity getRecommendProducts(@CookieValue(value = Constant.AUTH_ECOM_SESSION_KEY) String sessionId) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        if (StringUtils.isNotBlank(sessionId)) {
            Optional<UserSession> opUs = sessionRepo.findById(sessionId);
            if (opUs.isPresent()) {
                String userId = opUs.get().getUserId();

                // get list productId recommend
                // build list productItem
                // set data response
            } else {
                error = ErrorDefinition.ERR_NO_SESSION;
            }
        } else {
            error = ErrorDefinition.ERR_NO_SESSION;
        }
        RestResponseEntity resp = new RestResponseEntity(error, data);
        return resp;
    }
}
