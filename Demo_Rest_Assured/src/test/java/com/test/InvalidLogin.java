package com.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class InvalidLogin {
  @Test
  public void f() {
	  Map<String,Object> payload=new HashMap<String, Object>();
	  payload.put("username","adm");
	  payload.put("password","admi23");
	  Response res= RestAssured.given().contentType(ContentType.JSON).body(payload).when().post("http://localhost:5000/login");
	  System.out.println(res.statusCode());
	  Assert.assertEquals(res.statusCode(),401);
  }
}
