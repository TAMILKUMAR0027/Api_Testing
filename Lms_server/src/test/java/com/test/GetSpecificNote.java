package com.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;
import utils.LoadProperties;

public class GetSpecificNote {
	@Test(dataProvider = "data", dataProviderClass = ExcelUtils.class)
	public void getSpecificNote(String email, String password) {
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("email", email);
		payload.put("password", password);
		Response res = RestAssured.given().contentType(ContentType.JSON).body(payload)
				.post(LoadProperties.getProp().getProperty("loginUrl"));
		String token = res.jsonPath().getString("token");
		Response resStructure = RestAssured.given().header("Authorization", "Bearer " + token).pathParam("id", "6a324e6ad22ace8c6bac3c40")
				.when().get("https://lms-server-3-wedg.onrender.com/getById/notes/{id}");
		resStructure.prettyPrint();
	}
}
