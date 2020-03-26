package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengxuechao
 * @date 2020/3/23
 */
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @GetMapping("users/{username}")
    public User getUser(@PathVariable String username) {
        return new User(username, 18);
    }

    @GetMapping("v2/users/{username}")
    public User getUserV2(@PathVariable String username) {
        return new User(username, 18, 0);
    }
}
