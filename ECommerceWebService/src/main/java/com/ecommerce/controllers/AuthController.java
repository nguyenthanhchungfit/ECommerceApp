/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import com.ecommerce.common.Constant;
import com.ecommerce.common.ErrorDefinition;
import com.ecommerce.entities.AuthResult;
import com.ecommerce.entities.RestResponseEntity;
import com.ecommerce.model.data.mongodb.entity.Account;
import com.ecommerce.model.data.mongodb.repository.AccountRepository;
import com.ecommerce.model.data.redis.UserSession;
import com.ecommerce.model.data.redis.UserSessionRepository;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class AuthController {

    @Autowired
    private UserSessionRepository sessionRepo;

    @Autowired
    private AccountRepository accountRepo;

    private static final long EXPIRED_TIME = TimeUnit.DAYS.toMillis(7);// 7 days

    @PostMapping("/api/login")
    public RestResponseEntity doLogin(HttpServletResponse response, @RequestParam(name = "username") String userName,
        @RequestParam(name = "password") String password) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
            String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes());
            Account account = accountRepo.getAccountByUserName(userName);
            if (account != null && md5Hex.equals(account.getPassword())) {
                long currentTime = System.currentTimeMillis();
                long expiredTime = currentTime + EXPIRED_TIME;
                UserSession us = new UserSession(currentTime, expiredTime, Integer.valueOf(account.getUserId()));
                UserSession retUs = sessionRepo.save(us);
                if (retUs != null) {
                    String sessionId = retUs.getId();
                    Cookie cookie = _createCookie(Constant.AUTH_ECOM_SESSION_KEY, sessionId, (int) (EXPIRED_TIME / 1000));
                    response.addCookie(cookie);
                } else {
                    error = ErrorDefinition.ERR_CREATE_SESSION_FAILED;
                }

            } else {
                error = ErrorDefinition.ERR_WRONG_AUTH;
            }
        } else {
            error = ErrorDefinition.ERR_INVALID_PARAM;
        }
        RestResponseEntity resp = new RestResponseEntity(error, data);
        return resp;
    }

    @GetMapping("/api/logout")
    public RestResponseEntity doLogout(@CookieValue(value = Constant.AUTH_ECOM_SESSION_KEY) String sessionId,
        HttpServletResponse response) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        if (StringUtils.isNotBlank(sessionId)) {
            sessionRepo.deleteById(sessionId);
        }
        Cookie cookie = _createCookie(Constant.AUTH_ECOM_SESSION_KEY, null, 0);
        response.addCookie(cookie);
        RestResponseEntity resp = new RestResponseEntity(error, data);
        return resp;
    }

    @PostMapping("/api/register")
    public RestResponseEntity doRegister(@RequestParam(name = "account") String account,
        @RequestParam(name = "password") String password) {
        return null;
    }

    private Cookie _createCookie(String key, String sessionId, int maxAgeInSec) {
        Cookie cookie = new Cookie(key, sessionId);
        cookie.setMaxAge(maxAgeInSec);

        return cookie;
    }
}
