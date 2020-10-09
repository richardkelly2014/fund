package com.fund.client;

import com.alibaba.fastjson.JSON;
import com.fund.client.model.TushareRequestModel;
import com.fund.client.model.TushareStockBasicModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * TushareClient 数据
 */
@Service("tushareClient")
@Slf4j
public class TushareClient implements StockClient {

    private final static String token = "707f55c17d3cd27085c286972051d7c9202445e200a7b92963703909";

    private final static String api = "http://api.tushare.pro";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<List<String>> loadAllStock() {
        TushareRequestModel requestModel = TushareRequestModel.builder()
                .apiName("stock_basic")
                .token(token)
                .build();

        String postData = JSON.toJSONString(requestModel);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));

        HttpEntity<String> formEntity = new HttpEntity<>(postData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(api, formEntity, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();

            TushareStockBasicModel model = JSON.parseObject(body, TushareStockBasicModel.class);

            return model.getData().getItems();

        } else {
            return Lists.newArrayList();
        }

    }
}
