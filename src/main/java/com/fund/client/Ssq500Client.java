package com.fund.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Slf4j
public class Ssq500Client implements SsqClient {

    //http://kaijiang.500.com/shtml/ssq/20068.shtml
    private final static String url = "http://kaijiang.500.com/shtml/ssq/";
    private final static String current_url = "http://kaijiang.500.com/ssq.shtml";

    private Pattern lssuePattern = Pattern.compile("<font class=\"cfont2\"><strong>(.*?)</strong></font>");


    @Autowired
    private RestTemplate restTemplateGbk;

    @Override
    public String current() {

        ResponseEntity<String> responseEntity = restTemplateGbk.getForEntity(current_url, String.class);
        String value = responseEntity.getBody();
        Matcher matcher = lssuePattern.matcher(value);
        while (matcher.find()) {
            log.info("{}", matcher.group(1));
        }

        return null;
    }

    @Override
    public String get(int lssueNo) {
        String getUrl = url + lssueNo + ".shtml";

        String value = restTemplateGbk.getForEntity(getUrl, String.class).getBody();

        log.info("{}", value);

        return null;
    }
}
