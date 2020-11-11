package com.fundApp.test;

import com.fund.dal.TestMapper;
import com.fund.model.TestFundBasicModel;
import com.fund.model.TestFundDailyModel;
import com.fund.util.DateTimeUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EastTest.class)
@SpringBootApplication(scanBasePackages = {"com.fund"})
@Slf4j
public class EastTest {

    private final static String FUND_URL = "https://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&per=20&code=%s&page=%s";
    public static final Integer unitBase = 10000;

    public static final Integer rateBase = 100;

    private Pattern trPattern = Pattern.compile("<tr>(.*?)</tr>");
    private Pattern tdPattern = Pattern.compile("<td>(\\d{4}-\\d{2}-\\d{2})</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?>(.*?)%</td><td.*?>(.*?)</td><td.*?>(.*?)</td><td.*?></td>");


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestMapper testMapper;

    @Test
    public void test1() {

        List<TestFundBasicModel> basicModels = testMapper.selectAllLimit(206, 1000);

        for (TestFundBasicModel basicModel : basicModels) {
            String fundCode = basicModel.getFundCode();


            for (int page = 1; page <= 20; page++) {
                String url = String.format(FUND_URL, fundCode, page);

                log.info("{},{}", fundCode, url);

                String body = restTemplate.getForObject(url, String.class);

                parse(body, fundCode);

                sleep(2000);
            }

        }
    }

    private void parse(String value, String fundCode) {
        try {
            Matcher matcher = trPattern.matcher(value);
            List<TestFundDailyModel> result = Lists.newArrayList();
            while (matcher.find()) {
                String trValue = matcher.group(1);
                Matcher itemMatcher = tdPattern.matcher(trValue);

                while (itemMatcher.find()) {
                    String day = itemMatcher.group(1);
                    int week = DateTimeUtil.getWeekByDay(day);
                    String unitValue = itemMatcher.group(2);
                    String grandValue = itemMatcher.group(3);
                    String rate = itemMatcher.group(4);
                    int type = 1;
                    if (rate.startsWith("-")) {
                        type = 2;
                        rate = rate.substring(1);
                    }

                    log.info("{},{},{},{},{},{},{}", fundCode, day, week, unitValue, grandValue, type, rate);

                    result.add(TestFundDailyModel.builder()
                            .day(day)
                            .week(week)
                            .fundCode(fundCode)
                            .rateType(type)
                            .unitValue((int) (Float.valueOf(unitValue) * unitBase))
                            .grandValue((int) (Float.valueOf(grandValue) * unitBase))
                            .rate((int) (Float.valueOf(rate) * rateBase))
                            .build());
                }
            }
            if (result.size() > 0) {
                testMapper.batchInsertFundDaily(result);
            }
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    private void sleep(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
