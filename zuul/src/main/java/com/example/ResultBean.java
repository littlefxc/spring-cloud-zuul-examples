package com.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author fengxuechao
 * @date 2020/3/26
 */
@Data
public class ResultBean<T> {

    private int code;

    private String message;

    private T data;

    public ResultBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBean(int code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public static ResultBean<Object> ok() {
        return new ResultBean<>(200, "success");
    }

    public static ResultBean<Object> ok(Object data) {
        return new ResultBean<>(200, "success", data);
    }

    public static ResultBean<Object> error(int code, String message) {
        return new ResultBean<>(code, message);
    }
}
