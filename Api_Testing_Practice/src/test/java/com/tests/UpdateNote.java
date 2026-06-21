package com.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateNote extends BaseTest {

    @Test
    public void updateNoteById() {
        String token = getToken();
        String id = getFirstNoteId(token);

        Map<String, String> update = new HashMap<>();
        update.put("title", data.getData("updateTitle"));
        update.put("content", data.getData("updateContent"));

        Response r = requestWithToken(token)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(update)
                .when()
                .put(baseURI + "update/notes/{id}")
                .then()
                .statusCode(200)
                .body("message", equalTo("Note updated successfully"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Note updated successfully");
    }
}
