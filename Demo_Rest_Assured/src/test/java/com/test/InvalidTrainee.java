package com.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class InvalidTrainee {
  @Test
  public void getInvalidTrainee() {
	  Response res=RestAssured.given().when().get("http://localhost:3000/trainees/8");
	  assertEquals(res.statusCode(),404);
	  res.prettyPrint();
  }
}
