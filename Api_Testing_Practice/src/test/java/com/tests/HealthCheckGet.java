package com.tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class HealthCheckGet extends BaseTest {
  @Test
  public void healthCheck() {
	  Response r = request()
			  .when()
			  .get(baseURI)
			  .then()
			  .statusCode(200)
			  .extract()
			  .response();
	  System.out.println("Health Check Done");
	  
	  
  }
}
