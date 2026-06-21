package com.tests;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;

public class TooglePinNote extends BaseTest {

    @Test
    public void togglePinNoteById() {
        String token = getToken();
        String id = getFirstNoteId(token);

        Response r = requestWithToken(token)
                .pathParam("id", id)
                .when()
                .put(baseURI + "toggle-pin/notes/{id}")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("message", containsString("successfully"))
                .body("data._id", equalTo(id))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Note pin toggled successfully");
    }
}
