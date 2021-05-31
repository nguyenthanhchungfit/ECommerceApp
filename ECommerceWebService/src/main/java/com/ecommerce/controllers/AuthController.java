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
import com.ecommerce.model.data.mongodb.entity.User;
import com.ecommerce.model.data.mongodb.repository.AccountRepository;
import com.ecommerce.model.data.redis.UserSession;
import com.ecommerce.model.data.redis.UserSessionRepository;
import com.ecommerce.repository.mongodb.UserRespository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UserSessionRepository sessionRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRespository userRepo;

    private static final long EXPIRED_TIME = TimeUnit.DAYS.toMillis(7);// 7 days

    @GetMapping("/api/login")
    public RestResponseEntity doLogin(HttpServletResponse response, @RequestParam(name = "account") String userName,
            @RequestParam(name = "password") String password) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        System.out.println(String.format("Auth- account: %s, password: %s", userName, password));
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
            //String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes());
            User user = userRepo.getUserByAccount(userName);
            System.out.println("user: " + user);
            if (user != null && password.equals(user.getPassword())) {
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
                    data = new AuthResult(user.getId(), user.getPassword());
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
    @GetMapping("/api/register")
    public RestResponseEntity doRegister(@RequestParam(name = "account", defaultValue = "") String account,
            @RequestParam(name = "password", defaultValue = "") String password,
            @RequestParam(name = "fullname", defaultValue = "") String fullname,
            @RequestParam(name = "email", defaultValue = "") String email,
            @RequestParam(name = "date_of_birth", defaultValue = "") String dateOfBirth,
            @RequestParam(name = "address", defaultValue = "") String address) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        User user = userRepo.save(new User(1, email, email, date, account, password, address));
        RestResponseEntity resp = new RestResponseEntity(ErrorDefinition.ERR_SUCCESS, user);
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
