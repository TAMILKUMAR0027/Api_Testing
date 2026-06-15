package com.tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetUser {
  @Test
  public void getUserData() {
	  Response res=RestAssured.given().when().get("https://jsonplaceholder.typicode.com/users/1");
	  System.out.println("Status code : "+res.getStatusCode());
	  res.prettyPrint();
	  assertEquals(res.getStatusCode(), 200);
	  String name=res.jsonPath().getString("name");
	  assertEquals("Leanne Graham", name);
	  System.out.println(res.getStatusCode());
	  
  }
}
