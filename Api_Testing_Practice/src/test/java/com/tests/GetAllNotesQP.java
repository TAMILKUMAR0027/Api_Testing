package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAllNotesQP extends BaseTest {

    @Test
    public void getAllNotes_withoutQueryParams() {

        String token = getToken();

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(baseURI + "getAll/notes")
                .then()
                .statusCode(200)
                .extract()
                .response();

        response.prettyPrint();

        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertNotNull(response.jsonPath().getList("data"));
    }

    @Test
    public void getAllNotes_withQueryParams() {

        String token = getToken();

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .queryParam("page", data.getData("page"))
                .queryParam("limit", data.getData("limit"))
                .queryParam("sortOrder", data.getData("sortOrder"))
                .when()
                .get(baseURI + "getAll/notes")
                .then()
                .statusCode(200)
                .extract()
                .response();

        response.prettyPrint();

        Assert.assertTrue(response.jsonPath().getBoolean("success"));
    }

    @Test
    public void getAllNotes_withDifferentQueryParams() {

        String token = getToken();

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .queryParam("page", 2)
                .queryParam("limit", 1)
                .queryParam("sortBy", "title")
                .queryParam("sortOrder", "asc")
                .when()
                .get(baseURI + "getAll/notes")
                .then()
                .statusCode(200)
                .extract()
                .response();

        response.prettyPrint();

        Assert.assertTrue(response.jsonPath().getBoolean("success"));
    }
}