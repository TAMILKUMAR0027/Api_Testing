package com.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;
import utils.LoadProperties;

public class GetAllNotes {
  @Test(dataProvider = "data",dataProviderClass = ExcelUtils.class)
  public void getAllNote(String email,String password) {
	  Map<String,Object> payload=new HashMap<String, Object>();
	  payload.put("email", email);
	  payload.put("password",password);
	  Response res=RestAssured.given()
			  .contentType(ContentType.JSON)
			  .body(payload)
			  .post(LoadProperties.getProp().getProperty("loginUrl"));
	  String token=res.jsonPath().getString("token");
	
	  Response resStructure=RestAssured.given().header("Authorization","Bearer "+token).queryParam("limit", 1)
			  .queryParam("sortOrder", "desc")
			  .when()
			  .get("https://lms-server-3-wedg.onrender.com/getAll/notes");
	  resStructure.prettyPrint();
  }
}
