package com.tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class ApiValidation {
  @Test
  public void getApi() {
	  String email=RestAssured.given().when().get("https://jsonplaceholder.typicode.com/users/1").jsonPath().getString("email");
	  System.out.println(email);
	  assertEquals(email,"Sincere@april.biz");
  }
}
