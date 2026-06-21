package com.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class GetAllCourseStructureTest extends BaseTest {

    @Test
    public void getAllCourse() {
        String token = getToken();

        Response r = requestWithToken(token)
                .when()
                .get(baseURI + "courses-structure/getAll")
                .then()
                .statusCode(200)
                .body("message[0].value", equalTo("Course structures retrieved successfully"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Course structures retrieved successfully");
    }
}
