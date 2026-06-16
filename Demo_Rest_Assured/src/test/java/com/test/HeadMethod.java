package com.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class HeadMethod {
  @Test
  public void getHeader() {
	  Response res=RestAssured.given().when().head("http://localhost:3000/trainees/0J8i1r3ZFfw");
	  System.out.println(res.getHeaders());
	  assertEquals(res.statusCode(),400);
  }
}
