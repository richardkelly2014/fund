package com.gp;

import com.alibaba.fastjson.JSON;
import com.fund.client.model.TushareRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class tuTest {

    private final static String token = "707f55c17d3cd27085c286972051d7c9202445e200a7b92963703909";

    private final static String api = "http://api.tushare.pro";

    private RestTemplate restTemplate;


    @Before
    public void install() {
        restTemplate = restTemplate();
    }


    @Test
    public void test1() {

        TushareRequestModel model = TushareRequestModel.builder()
                .apiName("stock_basic")
                .token(token)
                .build();

        String postData = JSON.toJSONString(model);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));

        HttpEntity<String> formEntity = new HttpEntity<>(postData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(api, formEntity, String.class);
        log.info("{}", responseEntity.getBody());
    }


    public RestTemplate restTemplate() {
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
