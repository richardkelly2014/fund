package com.fund.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Component
public class CustomRestTemplate {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        return restTemplate;
    }

    @Bean("restTemplateGbk")
    public RestTemplate restTemplateGBK() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("gb2312")));
        return restTemplate;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(10000);//单位为ms
        factory.setConnectTimeout(10000);//单位为ms
        return factory;
    }

}