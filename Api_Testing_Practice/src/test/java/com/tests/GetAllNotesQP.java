package com.tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class GetAllNotesQP extends BaseTest {

    @Test
    public void getAllNotesUsingQueryParameter() {
        String token = getToken();

        Response r = getAllNotes(token);
        r.prettyPrint();
        System.out.println("All notes Got based on filter results");
    }
}
