package com.test;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchChangeDetail {
  @Test
  public void setParticularData() {
	  Map<String,Object> payload=new HashMap<String, Object>();
	  String id="1";
	  payload.put("email","abc@gmail.com");
	  Response res=RestAssured.given().contentType(ContentType.JSON).pathParam("id", id).body(payload).when().patch("http://localhost:3000/trainees/{id}");
	  assertEquals(res.statusCode(),200);
	  res.prettyPrint();
  }
}
