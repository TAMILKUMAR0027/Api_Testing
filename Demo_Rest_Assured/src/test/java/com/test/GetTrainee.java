package com.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetTrainee {
  @Test
  public void getTrainee() {
	  Response res=RestAssured.given().when().get("http://localhost:3000/trainees");
	  assertEquals(res.statusCode(),200);
	  res.prettyPrint();
  }
}
