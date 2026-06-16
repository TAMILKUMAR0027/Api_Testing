package com.test;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateTrainee {
  @Test
  public void updateTrainee() {
	  Map<String,Object> payload=new HashMap<String, Object>();
	  payload.put("name","Tamil");
	  payload.put("email","qwe@gmail.com");
	  payload.put("company","Capgemni");
	  String id="MPsQM34A714";
	  Response res=RestAssured.given().contentType(ContentType.JSON).pathParam("id", id).body(payload).put("http://localhost:3000/trainees/{id}");
	  assertEquals(res.statusCode(),200);
	  res.prettyPrint();
  }
}
