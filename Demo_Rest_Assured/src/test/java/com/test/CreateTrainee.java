package com.test;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateTrainee {
  @Test
  public void setTrainee() {
	  Map<String, Object> payload=new HashMap<String, Object>();
	  payload.put("name","Jeeva");
	  payload.put("email","jeeva@gmail.com");
	  payload.put("company","Zoho");
	  Response res=RestAssured.given().contentType(ContentType.JSON).body(payload).when().post("http://localhost:3000/trainees");
	  System.out.println(res.statusCode());
	  assertEquals(res.statusCode(),201);
	  res.prettyPrint();
  }
}
