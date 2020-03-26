package com.example;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fengxuechao
 * @date 2020/3/23
 */
@Data
public class User {

    private String username;

    private Integer age;

    private Integer sex;

    public User(){}

    public User(String username, int age) {
        this.username = username;
        this.age = age;
        this.sex = 1;
    }

    public User(String username, int age, int sex) {
        this.username = username;
        this.age = age;
        this.sex = sex;
    }
}
