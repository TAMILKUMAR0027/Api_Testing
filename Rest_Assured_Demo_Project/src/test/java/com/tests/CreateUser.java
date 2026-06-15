package com.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUser {
  @Test
  public void CreateUserData() {
	  Map<String,Object> payload=new HashMap<String, Object>();
	  payload.put("title","My first post");
	  payload.put("body","Learning assured");
	  payload.put("userId",1);
	  Response res=RestAssured.given().contentType(ContentType.JSON).body(payload).when().post("https://jsonplaceholder.typicode.com/posts");
	  System.out.print("Status code : "+res.getStatusCode());
	  res.prettyPrint();
	  Assert.assertEquals(res.getStatusCode(), 201);
	  Assert.assertEquals(res.jsonPath().getString("title"), "My first post");
	  
  }
}
