package com.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchMethod {

    @Test
    public void patchPost() {

        Map<String, Object> payload = new HashMap<>();
        Response res=RestAssured.given().when().get("https://jsonplaceholder.typicode.com/posts/1");
        res.prettyPrint();
        payload.put("title", "Updated Title");

        Response res1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .patch("https://jsonplaceholder.typicode.com/posts/1");

        res1.prettyPrint();

    }
}