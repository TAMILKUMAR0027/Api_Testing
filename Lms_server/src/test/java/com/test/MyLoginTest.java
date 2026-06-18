package com.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MyLoginTest {
  @Test
  public void loginTest() {
	  Map<String,Object> pay=new HashMap<String, Object>();
	  pay.put("email", "sam@gmail.com");
	  pay.put("password","123");
	  Response res=RestAssured.given().contentType(ContentType.JSON).body(pay).when().post("https://api-testing-bh4j.onrender.com/user/login");
	  String token=res.jsonPath().getString("data.token");
      
	  System.out.println(token);
  }
}