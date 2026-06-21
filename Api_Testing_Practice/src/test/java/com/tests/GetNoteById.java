package com.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class GetNoteById extends BaseTest {

    @Test
    public void getNoteSpecificById() {

        String token = getToken();
        String id = getFirstNoteId(token);

        Response r = requestWithToken(token)
                .pathParam("id", id)
                .when()
                .get(baseURI + "getById/notes/{id}")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Note of Specific ID Details Retrieved");
    }

    @Test
    public void getNoteByInvalidId() {

        String token = getToken();

        Response r = requestWithToken(token)
                .pathParam("id", "6a321e2bd22ace8c6babeef0")
                .when()
                .get(baseURI + "getById/notes/{id}")
                .then()
                .statusCode(404)
                .body("success", equalTo(false))
                .body("message", equalTo("Note not found"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Invalid Note ID Validation Passed");
    }
}