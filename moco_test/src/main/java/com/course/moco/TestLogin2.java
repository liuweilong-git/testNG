package com.course.moco;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;


public class TestLogin2 {

    @Test
    public void testLogin4(){
        Response res =
                given().queryParam("name", "huhansan").
                        queryParam("sex", "20").
                        when()
                        .get(" http://localhost:8888/getwithparam")
                        .then()
                        .extract().response();
        System.out.println(res.time());
        System.out.println(res.getBody().asString());
        System.out.println(res.statusCode());
        System.out.println(res.getHeaders());
        System.out.println(res.jsonPath().get("Content-Type").toString());
//        System.out.println("用户名：" + username + "密码：" + password);
    }

//    @Test
//    public void testUpdate(){
//
//        given().
//                contentType("multipart/from-data").
//                multiPart(new File("")).
//        when().
//                post("https://httpbin.org/post").
//        then().log().body().extract().response();
//    }



}
