package com.tests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class HeaderMethod {

    @Test
    public void headPost() {

        Response res = RestAssured.given()
                .when()
                .head("https://jsonplaceholder.typicode.com/posts/1");

        System.out.println(res.getHeaders());

    }
}