package com.test;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;
import utils.LoadProperties;

public class LoginTest {
    public static String token;
    @Test(dataProvider = "data",dataProviderClass = ExcelUtils.class)
    public void validLogin(String email,String password) {

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("email", email);
        payload.put("password", password);

        Response res = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(LoadProperties.getProp().getProperty("loginUrl"));
        token = res.jsonPath().getString("token");
        System.out.println("Token: " + token);
        System.out.println("Institution : "+res.jsonPath().getString("institution"));
        System.out.println("Instituion name : "+res.jsonPath().getString("institutionName"));
        System.out.println("User : "+res.jsonPath().getString("userId"));
        assertEquals(res.getStatusCode(), 201);
    }
}

