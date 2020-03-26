# Spring Cloud Zuul Examples

这是 Spring Cloud Zuul 学习示例集。

同时作为公司培训实习生的PPT的素材来源。

## 简介

路由是微服务架构不可或缺的一部分。例如，`/`可能被映射到您的Web应用程序，`/api/users`被映射到用户服务以及`/api/shop`被映射到商店服务。
[Zuul](https://github.com/Netflix/zuul)是Netflix提供的基于JVM的路由器和服务器端负载平衡器。

Netflix将Zuul用于以下用途：

- 权限控制和安全性：可以识别认证需要的信息和拒绝不满足条件的请求
- 监控：监控请求信息
- 动态路由：根据需要动态地路由请求到后台的不同服务集群
- 压力测试：逐渐增大到集群的流量，以便进行性能评估
- 灰度测试：在某项产品或应用正式发布前，选择特定人群试用，逐步扩大其试用者数量，以便及时发现和纠正其中的问题
- 负载均衡：为每种类型的请求分配容量并丢弃超过限额的请求
- 限流
- 黑白名单过滤
- 静态资源处理：直接在zuul处理静态资源的响应而不需要转发这些请求到内部集群中

Zuul的规则引擎使规则和过滤器基本上可以用任何JVM语言编写，并具有对Java和Groovy的内置支持。

## 如何使用 Zuul

```mxml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

```java

```

## 嵌入式反向代理

Spring Cloud创建了一个嵌入式Zuul代理，以简化UI应用程序要对一个或多个后端服务进行代理调用的常见用例的开发。
此功能对于用户界面代理所需的后端服务很有用，从而避免了为所有后端独立管理CORS和身份验证问题的需求。
