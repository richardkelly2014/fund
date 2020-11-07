package com.fundApp.test;

import com.fund.dal.TestMapper;
import com.fund.model.TestFundBasicModel;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MulThsFundTest.class)
@SpringBootApplication(scanBasePackages = {"com.fund"})
@Slf4j
public class MulThsFundTest {

    @Autowired
    private TestMapper testMapper;


    @Test
    public void test1() throws IOException {
        int startId = 4000;
        for (int i = 0; i < 8; i++) {
            int start = startId + (i * 1000);
            int end = start + 1000;

            List<TestFundBasicModel> list = testMapper.selectAllLimit(start, end);

            log.info("{},{},{}", start, end, list.size());

            if (list.size() > 0) {
                TaskRunnable task = new TaskRunnable(list, testMapper);

                Thread thread = new Thread(task);
                thread.start();
            }

        }

        System.in.read();
    }


    public static class TaskRunnable implements Runnable {
        String urlFormatter = "http://fund.10jqka.com.cn/%s/interduce.html#interduce";

        List<TestFundBasicModel> list;

        private TestMapper testMapper;

        public TaskRunnable(List<TestFundBasicModel> data, TestMapper testMapper) {
            this.list = data;
            this.testMapper = testMapper;
        }

        @Override
        public void run() {
            ChromeComponent chromeComponent = new ChromeComponent();

            for (TestFundBasicModel basicModel : list) {
                String fundCode = basicModel.getFundCode();
                String url = String.format(urlFormatter, fundCode);

                log.info("{}", url);
                chromeComponent.get(url);
                sleep();

                String html = chromeComponent.html();

                Document document = Jsoup.parse(html);

                Element element = document.getElementsByClass("ti-left").first();
                if (element == null) {
                    testMapper.updateFundBasicStatus(fundCode, 2);
                    continue;
                }
                String type = element.child(2).text();
                String risk = element.child(3).text();

                Element baseElement = document.getElementsByClass("g-dialog").first();

                String investType = baseElement.child(4).child(1).text();
                String companyName = baseElement.child(12).child(1).text();

                log.info("{},{},{},{}", type, risk, investType, companyName);

                testMapper.updateFundBasic(fundCode, type, investType, risk, companyName);
            }

            chromeComponent.quit();
        }

        private void sleep() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
