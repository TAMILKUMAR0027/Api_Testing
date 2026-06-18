package com.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;
import utils.LoadProperties;

public class GetAllCourseStructure {
  @Test(dataProvider = "data",dataProviderClass = ExcelUtils.class)
  public void getAllCourse(String email,String password) {
	  Map<String,Object> payload=new HashMap<String, Object>();
	  payload.put("email", email);
	  payload.put("password",password);
	  Response res=RestAssured.given()
			  .contentType(ContentType.JSON)
			  .body(payload)
			  .post(LoadProperties.getProp().getProperty("loginUrl"));
	  String token=res.jsonPath().getString("token");
	  Response resStructure=RestAssured.given().header("Authorization","Bearer "+token)
			  .when().get(LoadProperties.getProp().getProperty("getAllCourses"));
	  resStructure.prettyPrint();
	  resStructure.then().statusCode(200);
  }
}
