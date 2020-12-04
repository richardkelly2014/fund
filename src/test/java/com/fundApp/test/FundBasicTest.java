package com.fundApp.test;

import com.fund.dal.TestMapper;
import com.fund.model.FundBaseModel;
import com.fund.model.TestFundBasicModel;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FundBasicTest.class)
@SpringBootApplication(scanBasePackages = {"com.fund"})
@Slf4j
public class FundBasicTest {

    public final static String FUND_URL = "http://fundf10.eastmoney.com/jbgk_%s.html";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestMapper testMapper;

    @Test
    public void test1() {

        List<TestFundBasicModel> list = testMapper.selectAll();

        for (TestFundBasicModel model : list) {
            String fundCode = model.getFundCode();

            String url = String.format(FUND_URL, fundCode);

            String body = restTemplate.getForObject(url, String.class);

            Document document = Jsoup.parse(body);

            Elements elements = document.getElementsByClass("info w790");
            Element element = elements.get(0).child(0);

            Element date = element.child(2);
            Element scale = element.child(3);


            log.info("{}---{},{}---{},{}", fundCode, date.child(1).text(), date.child(3).text(),
                    scale.child(1).text(), scale.child(3).text());

        }

    }

}
