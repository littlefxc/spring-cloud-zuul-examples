package com.example;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志过滤器
 * <p>
 * 如何禁用该过滤器？
 * 配置文件中添加 zuul.LogFilter.pre.disable=true
 *
 * @author fengxuechao
 * @date 2020/3/25
 */
@Slf4j
@Component
public class LogFilter extends ZuulFilter {

    /**
     * 过滤器类型
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器优先级
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 允许过滤
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 如果 shouldFilter() 返回 true，就会调用 run() 方法
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("打印日志 : send {} request to {}",
                request.getMethod(), request.getRequestURL().toString());
        String header = request.getHeader("deviceId");
        log.info("请求头：deviceId:{}", header);

//        testErrorHandle();

        return null;
    }

    private void testErrorHandle() throws ZuulException {

        // 模拟过滤器抛出的异常
//        throw new ZuulException("异常消息", 400, "错误原因");

        // 模拟其它未知异常
        try {
            throw new Exception("未知异常");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZuulRuntimeException(e);
        }
    }
}
