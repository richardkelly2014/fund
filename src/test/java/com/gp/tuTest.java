package com.gp;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.fund.client.model.TushareRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
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

    @Test
    public void test1122() {

        log.info("{}", restTemplate.getForObject("http://www.baidu.com", String.class));
    }

    @Test
    public void test2() {


        Map<String, String> dailyMap = new HashMap<>();
        dailyMap.put("ts_code", "601633.SH");
        dailyMap.put("start_date", "20200920");
        dailyMap.put("end_date", "20200930");

        TushareRequestModel model = TushareRequestModel.builder()
                .apiName("daily")
                .token(token)
                .params(dailyMap)
                .build();

        String postData = JSON.toJSONString(model);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));

        HttpEntity<String> formEntity = new HttpEntity<>(postData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(api, formEntity, String.class);
        log.info("{}", responseEntity.getBody());
    }

    @Test
    public void test3() {
        String v = "601633.SH";
        String[] vs = StringUtils.split(v, ".");
        log.info("{},{}", v.substring(0, v.indexOf(".")), vs[0]);
    }


    @Test
    public void test4() {
        String endDateStr = DateUtil.yesterday().toDateStr();
        String beginDateStr = DateUtil.yesterday().offset(DateField.DAY_OF_YEAR, -30).toDateStr();

        log.info("{},{}", endDateStr, beginDateStr);
    }


    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);//单位为ms
        factory.setConnectTimeout(30000);//单位为ms
        return factory;
    }
}
