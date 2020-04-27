package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fengxuechao
 * @date 2020/4/27
 */
@Slf4j
@RestController
public class UsersController {

    @GetMapping("users/{username}")
    public User getUser(@PathVariable String username, HttpServletRequest request) {
        String deviceId = request.getHeader("deviceId");
        log.info("header.deviceId = {}", deviceId);
        log.info("header.Authorization = {}", request.getHeader("Authorization"));
        return new User(username, 18);
//        throw new RuntimeException("运行时异常");
    }

    @GetMapping("v2/users/{username}")
    public User getUserV2(@PathVariable String username, HttpServletRequest request) {
        String deviceId = request.getHeader("deviceId");
        log.info("header.deviceId = {}", deviceId);
        log.info("header.Authorization = {}", request.getHeader("Authorization"));
        return new User(username, 18, 0);
    }
}
