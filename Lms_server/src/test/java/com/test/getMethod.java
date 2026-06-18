package com.test;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.LoadProperties;

public class getMethod {
  @Test
  public void getAllInstituion() {
	  Response res=RestAssured.given()
			  .when()
			  .get(LoadProperties.getProp().getProperty("getAllInstituionUrl"));
	  res.prettyPrint();
  }
}
