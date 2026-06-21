package com.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class GetInstitutionTest extends BaseTest {

    @Test
    public void getAllInstitution() {

        Response r = request()
                .when()
                .get(baseURI + "getAll/institution")
                .then()
                .statusCode(200)
                .body("message[0].value",
                        equalTo("Institution Retrieved successfully"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("All Institutions Retrieved Successfully");
    }

    @Test
    public void getAllInstitution_InvalidEndpoint() {

        Response r = request()
                .when()
                .get(baseURI + "getAll/institutions")
                .then()
                .statusCode(404)
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Invalid Endpoint Validation Passed");
    }
}