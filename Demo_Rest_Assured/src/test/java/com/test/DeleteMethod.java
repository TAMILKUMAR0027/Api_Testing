package com.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteMethod {
  @Test
  public void deleteTrainee() {
	  Response res=RestAssured.given().when().get("http://localhost:3000/trainees/4JsY6_zYv0Y");
	  
	  res.prettyPrint();
	  Response res1=RestAssured.given().when().delete("http://localhost:3000/trainees/4JsY6_zYv0Y");
	  assertEquals(res1.statusCode(),200);
	  
  }
}
