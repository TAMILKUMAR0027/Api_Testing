package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;
import utils.LoadProperties;

public class CreatePost {
	  @Test(dataProvider = "data",dataProviderClass = ExcelUtils.class)

  public void createPost(String email,String password) {
		  Map<String,Object> payload=new HashMap<String, Object>();
		  payload.put("email", email);
		  payload.put("password",password);
		  List<String> tags=new ArrayList<String>();
		  tags.add("qa");
		  tags.add("demo");
		  Map<String,Object> noteData=new HashMap<String, Object>();
		  noteData.put("title", "notes");
		  noteData.put("content", "created by tester");
		  noteData.put("tags",tags);
		  noteData.put("color", "green");
		  noteData.put("isPinned", "false");
		  Response res=RestAssured.given()
				  .contentType(ContentType.JSON)
				  .body(payload)
				  .post(LoadProperties.getProp().getProperty("loginUrl"));
		  String token=res.jsonPath().getString("token");
		  Response resStructure=RestAssured.given().contentType(ContentType.JSON).header("Authorization","Bearer "+token).body(noteData)
				  .when().post(LoadProperties.getProp().getProperty("noteUrl"));
		  resStructure.prettyPrint();
  }
}
