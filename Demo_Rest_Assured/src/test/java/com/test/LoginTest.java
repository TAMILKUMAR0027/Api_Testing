package com.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class LoginTest {
  @Test
  public void validLogin() {
	  Map<String,Object> payload=new HashMap<String, Object>();
	  payload.put("username","admin");
	  payload.put("password","admin123");
	  Response res= RestAssured.given().contentType(ContentType.JSON).body(payload).when().post("http://localhost:5000/login");
	  System.out.println(res.statusCode());
	  Assert.assertEquals(res.statusCode(),200);
	  String token=res.jsonPath().getString("token");
	  System.out.println(token);
  }
}
