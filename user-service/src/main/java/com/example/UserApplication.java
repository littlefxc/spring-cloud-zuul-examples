package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengxuechao
 * @date 2020/3/23
 */
@Slf4j
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @GetMapping("users/{username}")
    public User getUser(@PathVariable String username) {
//        return new User(username, 18);
        throw new RuntimeException("运行时异常");
    }

    @GetMapping("v2/users/{username}")
    public User getUserV2(@PathVariable String username) {
        return new User(username, 18, 0);
    }

    /*@ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(RuntimeException e) {
        log.error("", e);
        return ResultBean.error(400, e.getMessage());
    }*/
}
