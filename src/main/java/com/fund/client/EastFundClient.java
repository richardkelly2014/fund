package com.fund.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class EastFundClient implements FundClient {

    private final static String ListURL = "http://fund.eastmoney.com/f10/F10DataApi.aspx";
    private Pattern trPattern = Pattern.compile("<tr>(.*?)</tr>");
    private Pattern tdPattern = Pattern.compile("<td>(\\d{4}-\\d{2}-\\d{2})</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?></td>");

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void findFundHistory() {

        String url = ListURL + "?type=lsjz&code=000083";
        String value = restTemplate.getForObject(url, String.class);

        Matcher matcher = trPattern.matcher(value);
        while (matcher.find()) {
            String trValue = matcher.group(1);

            Matcher itemMatcher = tdPattern.matcher(trValue);

            while (itemMatcher.find()) {

                log.info("{},{},{},{},{},{},{}", itemMatcher.group(1),
                        itemMatcher.group(2), itemMatcher.group(3), itemMatcher.group(4),
                        itemMatcher.group(5), itemMatcher.group(6));
            }


        }

        log.info("{}", value);
    }
}
