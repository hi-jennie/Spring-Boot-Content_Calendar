package dev.danvega.content_calendar.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//当 Spring 容器扫描到 MyWebConfig 上的 @Configuration 注解后，会处理该类，
// 并注册所有标注了 @Bean 的方法返回的对象为 Spring Bean。
@Configuration
public class MyWebConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
