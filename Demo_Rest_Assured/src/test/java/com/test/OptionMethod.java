package com.test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OptionMethod {
  @Test
  public void getOption() {
	  Response res=RestAssured.given().when().options("http://localhost:3000/trainees");
	  System.out.println(res.statusCode());
	  System.out.println(res.statusLine());
  }
}
