package com.fundApp.test;

import com.fund.dal.TestMapper;
import com.fund.model.Test2Model;
import com.fund.model.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HtmlUnitTest.class)
@SpringBootApplication(scanBasePackages = {"com.fund"})
@Slf4j
public class HtmlUnitTest {

    private WebDriver chrome;

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void before() {

        System.setProperty("webdriver.chrome.driver", "db/chromedriver");


        ChromeOptions options = new ChromeOptions();

        //是否启用浏览器界面的参数
        //无界面参数
        //options.addArguments("headless");
        //禁用沙盒 就是被这个参数搞了一天
        //options.addArguments("no-sandbox");

        chrome = new ChromeDriver(options);


    }

    @Test
    public void test0() {
        String url = "http://q.10jqka.com.cn/gn/";
        String body = restTemplate.getForObject(url, String.class);
        Document document = Jsoup.parse(body);

        Elements elements = document.getElementsByClass("cate_items");
        for (Element element : elements) {
            Elements childs = element.children();
            for (Element child : childs) {
                String name = child.text();
                String href = child.attr("href");


                String[] array = StringUtils.split(href, "/");
                String code = array[array.length - 1];

                TestModel testModel = TestModel.builder()
                        .industryName(name)
                        .type(1)
                        .industryCode(code)
                        .source(1)
                        .build();

                log.info("{},{},{},{}", name, code, href, 1);

                testMapper.insert(testModel);

                detail(name, code, 1);
                //dyDetail(href, name, 4);

            }
        }
    }

    String dd = "http://q.10jqka.com.cn/gn/detail/code/%s/";


    private void detail(String name, String code, int type) {
        String url = String.format(dd, code);

        chrome.get(url);
        sleep();

        String html = chrome.getPageSource();
        next(html, name, code, type);
    }

    private void next(String html, String name, String code, int type) {
        //处理数据

        parse(html, name, code, type);
        try {
            WebElement webElement = chrome.findElement(By.linkText("下一页"));
            if (webElement != null) {
                webElement.click();
                sleep();
                String body = chrome.getPageSource();

                next(body, name, code, type);
            }
        } catch (Exception e) {
            log.info("{}", e);
        }
    }


    private void parse(String html, String name, String code, int type) {
        //log.info("{}", html);
        Document document = Jsoup.parse(html);
        Element table = document.getElementsByClass("m-table m-pager-table").get(0);
        Element tBody = table.getElementsByTag("tbody").get(0);

        Elements elements = tBody.children();

        List<Test2Model> models = Lists.newArrayList();

        for (Element element : elements) {

            String stockCode = element.child(1).text();
            String stockName = element.child(2).text();

            models.add(Test2Model.builder()
                    .industryCode(code)
                    .industryName(name)
                    .industryType(type)
                    .stockName(stockName)
                    .stockCode(stockCode)
                    .build());

            log.info("{},{},{},{}", code, name, stockCode, stockName);
        }

        testMapper.batchInsert(models);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
