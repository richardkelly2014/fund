package com.fundApp.test;

import com.fund.util.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpProxyTest {


    private RestTemplate restTemplate;

    @Before
    public void install() {
        restTemplate = restTemplate();
    }


    @Test
    public void test1() {
//        String userName = "mr_coder@163.com";
//        String passWord = "JiangFei11";
//
//        String base64Creds = HttpRequest.Base64.encode(userName + ":" + passWord);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Proxy-Authorization", "Basic " + base64Creds);
//
//        log.info("{}", restTemplate.exchange("http://www.baidu.com", HttpMethod.GET, new HttpEntity<String>(headers), String.class));


        log.info("{}", restTemplate.getForObject("http://www.baidu.com", String.class));

    }


    public RestTemplate restTemplate() {

        String userName = "mr_coder@163.com";
        String passWord = "JiangFei11";

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.getInterceptors().add(new ProxyClientHttpRequestInterceptor(userName, passWord));
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(Charset.forName("gb2312")));

        return restTemplate;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);//单位为ms
        factory.setConnectTimeout(30000);//单位为ms


        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("125.66.17.86", 3617)));
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
