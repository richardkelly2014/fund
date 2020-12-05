package com.fundApp.test;

import com.fund.dal.TestMapper;
import com.fund.model.FundBaseModel;
import com.fund.model.TestFundBasicModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

            String issue_date = date.child(1).text();

            if (StringUtils.isNotBlank(issue_date)) {
                issue_date = issue_date.replace("年", "-");
                issue_date = issue_date.replace("月", "-");
                issue_date = issue_date.replace("日", "");
            }

            String setup_date = date.child(3).text();
            if (StringUtils.isNotBlank(setup_date)) {
                setup_date = StringUtils.split(setup_date, "/")[0];

                setup_date = setup_date.replace("年", "-");
                setup_date = setup_date.replace("月", "-");
                setup_date = setup_date.replace("日", "").trim();
            }

            String asset_scale = scale.child(1).text();

            if (StringUtils.isNotBlank(asset_scale)) {
                int start = asset_scale.indexOf("亿");
                if (start >= 0) {
                    asset_scale = asset_scale.substring(0, start);
                } else {
                    asset_scale = "0";
                }
            }

            String share_scale = scale.child(3).text();
            if (StringUtils.isNotBlank(share_scale)) {
                int start = share_scale.indexOf("亿");
                if (start >= 0) {
                    share_scale = share_scale.substring(0, start);
                } else {
                    share_scale = "0";
                }
            }


            log.info("{}---{},{}---{},{}", fundCode, issue_date, setup_date, asset_scale, share_scale);

            testMapper.updateFundBasicDate(fundCode, issue_date, setup_date, asset_scale, share_scale);
            log.info("=================");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //break;
        }

    }

}
