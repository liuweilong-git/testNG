package com.course.httpclient.demo;
//import com.sun.deploy.net.HttpResponse;
import org.apache.http.HttpResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyHttpClient {

    @Test
    public void test1() throws IOException {
        CookieStore cookieStore = new BasicCookieStore();

        CloseableHttpClient closeableHttpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
//        HttpClients.custom().setDefaultCookieStore(cookieStore).build();
//        HttpClient httpClient = HttpClientBuilder.create().build();
        String urlStr = "http://localhost:8888/user/getCookie";
//        构造http get请求对象
        HttpGet httpGet = new HttpGet(urlStr);
//        可关闭的响应对象
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpGet);
            /**
             HttpEntity不仅可以作为结果，也可以作为请求参数的实体，有很多的实现
             */
//            获取响应结果
            HttpEntity entity = response.getEntity();
//            工具类，对HttpEntity操作的工具类,不要忘记加字符编码
            String toStringResult = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            System.out.println(toStringResult);

            Header[] allHeaders = response.getAllHeaders();
            for (Header header : allHeaders) {
                System.out.println("打印头信息");
                System.out.println(header);
            }
            List<Cookie> cookies = cookieStore.getCookies();
            for (Cookie cookie : cookies) {
                System.out.println("输出cookie");
                System.out.println(cookie.getName());
                System.out.println(cookie.getValue());
            }
//            确保输出流关闭
            EntityUtils.consume(entity);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (closeableHttpClient != null) {
                try {
                    closeableHttpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void closeTest(){
        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet("http://localhost:8888/user/getCookie");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)){
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String respomseString = EntityUtils.toString(entity);
                    System.out.println("Response: " + respomseString);

                    String toStringResult = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    System.out.println(toStringResult);

                    Header[] allHeaders = response.getAllHeaders();
                    for (Header header : allHeaders) {
                        System.out.println("打印头信息");
                        System.out.println(header);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}