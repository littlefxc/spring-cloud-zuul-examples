package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * zuul网关调用后端feign接口失败，断路处理。
 * 测试：断开后端api服务，然后使用zuul路由地址访问测试。
 *
 * @author fengxuechao
 */
@Slf4j
@Component
public class ProviderFallbackProvider implements FallbackProvider {

    private final ObjectMapper objectMapper;

    public ProviderFallbackProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 服务id，如果需要所有调用都支持回退，则return "*"或return null
     *
     * @return 所有服务
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {

            @Override
            public InputStream getBody() throws IOException {
                return getFallbackBody();
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {
            }

        };
    }

    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            log.error("网关超时");
            return response(HttpStatus.GATEWAY_TIMEOUT);
        } else {
            log.error("系统错误");
            return response(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ClientHttpResponse response(final HttpStatus status)  {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                return getFallbackBody();
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    private InputStream getFallbackBody() throws IOException {
        RequestContext context = RequestContext.getCurrentContext();
        log.error("服务(serviceId:{}),URL={} 不可用", context.get("serviceId"), context.getRequest().getRequestURL());
        String msg = context.get("serviceId") + "服务不可用";
        String json = objectMapper.writeValueAsString(ResultBean.error(500, msg));
        return IOUtils.toInputStream(json, "UTF-8");
    }
}
