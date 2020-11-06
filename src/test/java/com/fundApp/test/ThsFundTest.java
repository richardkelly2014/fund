package com.fundApp.test;

import com.fund.dal.TestMapper;
import com.fund.model.TestFundThemeFundModel;
import com.fund.model.TestFundThemeModel;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Created by jiangfei on 2020/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ThsFundTest.class)
@SpringBootApplication(scanBasePackages = {"com.fund"})
@Slf4j
public class ThsFundTest {

    private WebDriver chrome;

    @Autowired
    private TestMapper testMapper;

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
    public void test1() {
        String url = "http://fund.10jqka.com.cn/fundtheme/index.html";
        chrome.get(url);
        sleep();

        WebElement webElement = chrome.findElement(By.className("titleSelected"));
        webElement.click();
        sleep();

        String html = chrome.getPageSource();

        Document document = Jsoup.parse(html);
        Element element = document.getElementById("themelist");
        Elements elements = element.children();
        List<TestFundThemeModel> list = Lists.newArrayList();
        for (Element e : elements) {
            String dataId = e.attr("data-id");
            String dataField = e.attr("data-field");
            String name = e.child(0).text();

            list.add(TestFundThemeModel.builder()
                    .dataField(dataField)
                    .dataId(dataId)
                    .themeName(name)
                    .build());
        }

        testMapper.batchInsertTheme(list);

    }

    @Test
    public void test2() {
        String url = "http://fund.10jqka.com.cn/fundtheme/index.html#key/%s/%s";
        List<TestFundThemeModel> list = testMapper.selectThemeAll();

        for (TestFundThemeModel themeModel : list) {

            String dataId = themeModel.getDataId();
            String name = themeModel.getThemeName();

            String u = String.format(url, dataId, name);

            log.info("{}", u);
            chrome.get(u);

            sleep();

            chrome.navigate().refresh();

            sleep();

            String html = chrome.getPageSource();

            Document document = Jsoup.parse(html);

            Element element = document.getElementById("tbodyList");

            Elements elements = element.children();

            List<TestFundThemeFundModel> themeFundModels = Lists.newArrayList();

            for (Element e : elements) {
                Element c = e.child(0).child(0);
                String fundCode = c.attr("data-code");
                String fundName = c.attr("data-name");
                themeFundModels.add(TestFundThemeFundModel.builder()
                        .dataId(dataId)
                        .themeName(name)
                        .fundCode(fundCode)
                        .fundName(fundName)
                        .build());
            }

            testMapper.batchInsertThemeFund(themeFundModels);

        }


    }

    @Test
    public void test3() {
        chrome.get("http://www.baidu.com");
        sleep();
        chrome.get("http://www.163.com");
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
