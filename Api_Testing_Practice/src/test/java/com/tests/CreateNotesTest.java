package com.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateNotesTest extends BaseTest {

    @Test
    public void postNewNotes() {
        String token = getToken();

        List<String> tags = new ArrayList<>();
        tags.add(data.getData("noteTag1"));
        tags.add(data.getData("noteTag2"));

        Map<String, Object> notes = new HashMap<>();
        notes.put("title", data.getData("noteTitle"));
        notes.put("content", data.getData("noteContent"));
        notes.put("tags", tags);
        notes.put("isPinned", Boolean.parseBoolean(data.getData("notePinned")));
        notes.put("color", data.getData("noteColor"));

        Response r = requestWithToken(token)
                .contentType(ContentType.JSON)
                .body(notes)
                .when()
                .post(baseURI + "create/notes")
                .then()
                .statusCode(201)
                .body("message", equalTo("Note created successfully"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Note Created Successfully");
    }
}
