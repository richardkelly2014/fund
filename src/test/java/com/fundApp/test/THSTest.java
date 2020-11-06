package com.fundApp.test;

import com.fund.dal.TestMapper;
import com.fund.model.Test2Model;
import com.fund.model.TestModel;
import com.fund.util.HttpRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jiangfei on 2020/10/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = THSTest.class)
@SpringBootApplication(scanBasePackages = {"com.fund"})
@Slf4j
public class THSTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestMapper testMapper;

    //@Before
    public void install() {
        restTemplate = restTemplate();
    }

    @Test
    public void test1() {
        String url = "http://q.10jqka.com.cn/zjhhy/";
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
                        .type(4)
                        .industryCode(code)
                        .source(1)
                        .build();

                log.info("{},{},{},{}", name, code, href, 4);

                testMapper.insert(testModel);

                //dyDetail(href, name, 4);

            }
        }
    }

    @Test
    public void test2() {

        dyDetail("C", "制造业", 4);
    }

    String urlFormatter = "http://q.10jqka.com.cn/dy/detail/field/199112/order/desc/page/%s/ajax/1/code/%s";

    String dd = "http://q.10jqka.com.cn/zjhhy/detail/field/199112/order/desc/page/%s/ajax/1/code/%s";

    public void dyDetail(String code, String name, int type) {

        int page = 1;
        int maxPage = 1;
        do {
            String get = String.format(dd, page, code);

            log.info("{},{}", page, get);

            String body = restTemplate.getForObject(get, String.class);

            log.info("{}", body);

            Document document = Jsoup.parse(body);
            Elements elements = document.getElementsByTag("tbody").get(0).children();

            boolean isGo = true;
            List<Test2Model> models = Lists.newArrayList();

            for (Element element : elements) {
                int size = element.childrenSize();
                if (size > 2) {
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
                } else {
                    isGo = false;
                    break;
                }
            }

            if (!isGo) {
                break;
            } else {
                if (models.size() > 0) {
                    testMapper.batchInsert(models);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                page = page + 1;
            }
        } while (true);


//        String body = restTemplate.getForObject(url, String.class);
//
//
//
//        String text = document.getElementsByClass("board-hq").get(0).child(0).text();
//        log.info("{}", text);
    }

    public RestTemplate restTemplate() {

        String userName = "mr_coder@163.com";
        String passWord = "JiangFei11";

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("gb2312")));
        restTemplate.getInterceptors().add(new ProxyClientHttpRequestInterceptor(userName, passWord));

        return restTemplate;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);//单位为ms
        factory.setConnectTimeout(30000);//单位为ms


        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("60.166.86.180", 5412)));
        return factory;
    }


    public static class ProxyClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        private final String username;
        private final String password;
        private final String base64;

        public ProxyClientHttpRequestInterceptor(String username, String password) {
            this.username = username;
            this.password = password;
            String base64Creds = HttpRequest.Base64.encode(username + ":" + password);
            this.base64 = base64Creds;
        }

        @Override
        public ClientHttpResponse intercept(org.springframework.http.HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            HttpHeaders headers = httpRequest.getHeaders();
            if (!headers.containsKey("Proxy-Authorization")) {
                headers.add("Proxy-Authorization", "Basic " + base64);
            }

            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }
    }
}