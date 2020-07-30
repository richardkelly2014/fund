package com.fund.client;

import com.fund.model.SsqModel;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Slf4j
public class Ssq500Client implements SsqClient {

    //http://kaijiang.500.com/shtml/ssq/20068.shtml
    private final static String url = "http://kaijiang.500.com/shtml/ssq/";
    private final static String current_url = "http://kaijiang.500.com/ssq.shtml";

    private Pattern lssuePattern = Pattern.compile("<font class=\"cfont2\"><strong>(.*?)</strong></font>");

    private Pattern redPattern = Pattern.compile("<li class=\"ball_red\">(.*?)</li>");
    private Pattern bluePattern = Pattern.compile("<li class=\"ball_blue\">(.*?)</li>");


    @Autowired
    private RestTemplate restTemplateGbk;

    @Override
    public int current() {

        ResponseEntity<String> responseEntity = restTemplateGbk.getForEntity(current_url, String.class);
        String value = responseEntity.getBody();
        Matcher matcher = lssuePattern.matcher(value);
        if (matcher.find()) {
            String issue = matcher.group(1);
            if (StringUtils.isNotBlank(issue)) {
                return Integer.parseInt(issue);
            }
        }
        return 0;
    }

    @Override
    public List<Integer> get(int lssueNo) {
        String getUrl = url + lssueNo + ".shtml";
        byte[] buffer = restTemplateGbk.getForObject(getUrl, byte[].class);

        String value = "";
        try {
            value = new String(buffer, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Matcher redMatcher = redPattern.matcher(value);
        List<Integer> result = Lists.newArrayList();
        while (redMatcher.find()) {
            String red = redMatcher.group(1);
            if (StringUtils.isNotBlank(red)) {
                result.add(Integer.parseInt(red));
            }
        }

        Matcher blueMatcher = bluePattern.matcher(value);
        if (blueMatcher.find()) {
            String blue = blueMatcher.group(1);
            if (StringUtils.isNotBlank(blue)) {
                result.add(Integer.parseInt(blue));
            }
        }
        return result;
    }
}
