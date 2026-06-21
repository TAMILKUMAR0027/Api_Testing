package com.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class EndToEndTest extends BaseTest {

    @Test
    public void notesEndToEndFlow() {

        String token = getToken();

        requestWithToken(token)
                .when()
                .get(baseURI + "roles/getAll")
                .then()
                .statusCode(200)
                .body("message[0].value",
                        equalTo("Role Retrieved successfully"));

        requestWithToken(token)
                .when()
                .get(baseURI + "courses-structure/getAll")
                .then()
                .statusCode(200);

        String noteId = requestWithToken(token)
                .body("{\"title\":\"Test\",\"content\":\"hi\"}")
                .when()
                .post(baseURI + "create/notes")
                .then()
                .statusCode(201)
                .extract()
                .path("data._id");

        System.out.println("Created Note ID : " + noteId);

        requestWithToken(token)
                .pathParam("id", noteId)
                .when()
                .get(baseURI + "getById/notes/{id}")
                .then()
                .statusCode(200)
                .body("success", equalTo(true));

        Response deleteResponse = requestWithToken(token)
                .pathParam("id", noteId)
                .when()
                .delete(baseURI + "delete/notes/ById/{id}")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message",
                        equalTo("Notes deleted successfully"))
                .extract()
                .response();

        deleteResponse.prettyPrint();

        System.out.println("End To End Flow Completed Successfully");
    }
}