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
import com.ecommerce.model.data.mongodb.entity.User;
import com.ecommerce.model.data.mongodb.repository.AccountRepository;
import com.ecommerce.model.data.redis.UserSession;
import com.ecommerce.model.data.redis.UserSessionRepository;
import com.ecommerce.repository.mongodb.UserRespository;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private UserRespository userRepo;

    private static final long EXPIRED_TIME = TimeUnit.DAYS.toMillis(7);// 7 days

    @CrossOrigin
    @GetMapping("/api/login")
    public RestResponseEntity doLogin(HttpServletResponse response, @RequestParam(name = "account") String userName,
        @RequestParam(name = "password") String password) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        System.out.println(String.format("Auth- account: %s, password: %s", userName, password));
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
            String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes());
            User user = userRepo.getUserByAccount(userName);
            System.out.println("user: " + user);
            if (user != null && md5Hex.equals(user.getPassword())) {
                long currentTime = System.currentTimeMillis();
                long expiredTime = currentTime + EXPIRED_TIME;
                UserSession us = new UserSession(currentTime, expiredTime, Integer.valueOf(user.getId()));
                UserSession retUs = sessionRepo.save(us);
                System.out.println("Session: " + retUs);
                if (retUs != null) {
                    String sessionId = retUs.getId();
                    Cookie cookie = _createCookie(Constant.AUTH_ECOM_SESSION_KEY, sessionId, (int) (EXPIRED_TIME / 1000));
                    System.out.println("cookie: " + cookie);
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

    @CrossOrigin
    @GetMapping("/api/logout")
    public RestResponseEntity doLogout(@CookieValue(value = Constant.AUTH_ECOM_SESSION_KEY, defaultValue = "") String sessionId,
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

    @CrossOrigin
    @GetMapping
    @PostMapping("/api/register")
    public RestResponseEntity doRegister(@RequestParam(name = "account", defaultValue = "") String account,
        @RequestParam(name = "password", defaultValue = "") String password) {
        System.out.println("aaaaaaaaaaaaaaaaaaa");
        System.out.println("account: " + account);
        System.out.println("password: " + password);
        RestResponseEntity resp = new RestResponseEntity(0, null);
        return resp;
    }

    private Cookie _createCookie(String key, String sessionId, int maxAgeInSec) {
        Cookie cookie = new Cookie(key, sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSec);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setVersion(0);
        return cookie;
    }
}
