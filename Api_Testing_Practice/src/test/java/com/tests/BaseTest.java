package com.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.utilities.PropertyFileReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    public static PropertyFileReader data = new PropertyFileReader();

    public static String baseURI = data.getData("baseURI");
    public static String email = data.getData("email");
    public static String password = data.getData("password");

    public static Map<String, String> loginPayload(String email, String password) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);
        return payload;
    }

    public static RequestSpecification request() {
        return RestAssured.given();
    }

    public static Response loginResponse(String email, String password) {
        return request()
                .contentType(ContentType.JSON)
                .body(loginPayload(email, password))
                .when()
                .post(baseURI + "user/login");
    }

    public static String getToken() {
        Response r = loginResponse(email, password);
        String token = r.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token is null. Login failed.");
        return token;
    }

    public static RequestSpecification requestWithToken(String token) {
        return request()
                .header("Authorization", "Bearer " + token);
    }

    public static Response getAllNotes(String token) {
        return requestWithToken(token)
                .queryParam("page", data.getData("page"))
                .queryParam("limit", data.getData("limit"))
                .queryParam("sortOrder", data.getData("sortOrder"))
                .when()
                .get(baseURI + "getAll/notes")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public static String getFirstNoteId(String token) {
        Response r = getAllNotes(token);
        String id = r.jsonPath().getString("data[0]._id");
        Assert.assertNotNull(id, "Note ID is null. No note found.");
        return id;
    }
}
