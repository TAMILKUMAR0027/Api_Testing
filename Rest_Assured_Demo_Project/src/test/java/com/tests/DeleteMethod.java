package com.tests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteMethod {
	@Test
	public void deletePost() {
		Response res1 = RestAssured.given().when().get("https://jsonplaceholder.typicode.com/posts/1");
		res1.prettyPrint();
	    Response res = RestAssured.given().when().delete("https://jsonplaceholder.typicode.com/posts/1");
	    System.out.println("-----------------------");
	    res.prettyPrint();
	}
}
