package com.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;
import utils.LoadProperties;

public class GetAllRoles {
	 @Test(dataProvider = "data",dataProviderClass = ExcelUtils.class)
  public void getAlltheRoll(String email,String password) {
	  Map<String,Object>loginData=new HashMap<String, Object>();
	  loginData.put("email",email);
	  loginData.put("password", password);
	  Response res=RestAssured.given().contentType(ContentType.JSON).body(loginData).post(LoadProperties.getProp().getProperty("getAllroleUrl"));
	  String token=res.jsonPath().getString("token");
	  Response rolesRes = RestAssured.given()
              .header("Authorization", "Bearer " + token).when().get("https://lms-server-3-wedg.onrender.com/roles/getAll");
      System.out.println("Status Code: " + rolesRes.getStatusCode());
      System.out.println("Response: ");
      System.out.println(rolesRes.getBody().asPrettyString());
  }
  
}
