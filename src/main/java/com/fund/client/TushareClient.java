package com.fund.client;

import com.alibaba.fastjson.JSON;
import com.fund.client.model.TushareRequestModel;
import com.fund.client.model.TushareStockBasicModel;
import com.fund.client.model.TushareStockDailyModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String body = post(postData);

        if (StringUtils.isNotBlank(body)) {
            TushareStockBasicModel model = JSON.parseObject(body, TushareStockBasicModel.class);

            return model.getData().getItems();
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public List<List<String>> loadStockByDay(String tsCode, String tradeDate, String startDate, String endDate) {

        Map<String, String> dailyMap = new HashMap<>();
        dailyMap.put("ts_code", tsCode);

        if (StringUtils.isNotBlank(tradeDate)) {
            dailyMap.put("trade_date", tradeDate);
        } else {
            dailyMap.put("start_date", startDate);
            dailyMap.put("end_date", endDate);
        }

        TushareRequestModel requestModel = TushareRequestModel.builder()
                .apiName("daily")
                .token(token)
                .params(dailyMap)
                .build();

        String postData = JSON.toJSONString(requestModel);
        String body = post(postData);

        if (StringUtils.isNotBlank(body)) {

            TushareStockDailyModel model = JSON.parseObject(body, TushareStockDailyModel.class);
            return model.getData().getItems();
        } else {
            return Lists.newArrayList();
        }
    }


    private String post(String postData) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));

        HttpEntity<String> formEntity = new HttpEntity<>(postData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(api, formEntity, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            return body;
        } else {
            return null;
        }
    }

}
