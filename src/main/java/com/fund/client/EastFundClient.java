package com.fund.client;

import com.fund.client.model.EastFundModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class EastFundClient implements FundClient {

    private final static String ListURL = "http://fund.eastmoney.com/f10/F10DataApi.aspx";
    private Pattern trPattern = Pattern.compile("<tr>(.*?)</tr>");
    private Pattern tdPattern = Pattern.compile("<td>(\\d{4}-\\d{2}-\\d{2})</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?>(.*?)%</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?></td>");

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<EastFundModel> findFundHistory(String code) {
        String url = ListURL + "?type=lsjz&per=20&code=" + code;
        return find(url);
    }

    @Override
    public List<EastFundModel> findFundHistory(String code, String sdate) {
        String url = ListURL + "?type=lsjz&per=20&code=" + code + "&sdate=" + sdate;
        return find(url);
    }

    private List<EastFundModel> find(String url) {
        String value = restTemplate.getForObject(url, String.class);
        if (StringUtils.isNotBlank(value)) {
            return parseObject(value);
        } else {
            return Lists.newArrayList();
        }
    }

    /**
     * 正则解析数据
     *
     * @param value
     * @return
     */
    private List<EastFundModel> parseObject(String value) {
        List<EastFundModel> result = Lists.newArrayList();
        Matcher matcher = trPattern.matcher(value);
        while (matcher.find()) {
            String trValue = matcher.group(1);
            Matcher itemMatcher = tdPattern.matcher(trValue);

            while (itemMatcher.find()) {
                String day = itemMatcher.group(1);
                String unitValue = itemMatcher.group(2);
                String grandValue = itemMatcher.group(3);
                String rate = itemMatcher.group(4);
                int type = 1;
                if (rate.startsWith("-")) {
                    type = 2;
                    rate = rate.substring(1);
                }
                result.add(EastFundModel.builder()
                        .day(day)
                        .unitValue(Float.valueOf(unitValue))
                        .grandValue(Float.valueOf(grandValue))
                        .type(type)
                        .rate(Float.valueOf(rate))
                        .build());
            }
        }
        return result;
    }

}
