package com.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class DeleteNote extends BaseTest {

    @Test
    public void deleteNoteById() {

        String token = getToken();
        String id = getFirstNoteId(token);

        Response r = requestWithToken(token)
                .pathParam("id", id)
                .when()
                .delete(baseURI + "delete/notes/ById/{id}")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", equalTo("Notes deleted successfully"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Note deleted successfully");
    }

    @Test
    public void deleteNoteWithInvalidIdFormat() {

        String token = getToken();

        Response r = requestWithToken(token)
                .pathParam("id", "invalid123")
                .when()
                .delete(baseURI + "delete/notes/ById/{id}")
                .then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Invalid note ID format"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Invalid Note ID Format Validation Passed");
    }
}