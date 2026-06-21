package com.tests;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;

public class GetRolesTest extends BaseTest {

    @Test
    public void getAllRoleswithToken() {
        String token = getToken();

        Response r = requestWithToken(token)
                .when()
                .get(baseURI + "roles/getAll")
                .then()
                .statusCode(200)
                .body("message[0].value", equalTo("Role Retrieved successfully"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Role Retrived Successfully");
    }

    @Test
    public void getAllRoleswithoutToken() {
        Response r = request()
                .when()
                .get(baseURI + "roles/getAll")
                .then()
                .statusCode(401)
                .body("message[0].value", equalTo("User is not logged in"))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("User Not Logged in [without token]");
    }
}
