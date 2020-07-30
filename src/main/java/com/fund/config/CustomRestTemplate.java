package com.fund.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


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

        return restTemplate;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);//单位为ms
        factory.setConnectTimeout(30000);//单位为ms
        return factory;
    }

}