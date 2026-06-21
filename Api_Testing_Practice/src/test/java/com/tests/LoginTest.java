package com.tests;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import com.utilities.DpExcel;

import io.restassured.response.Response;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "vData", dataProviderClass = DpExcel.class)
    public void validLogin(String email, String password, String expectedStatus, String expectedMessage) {
        Response r = loginResponse(email, password)
                .then()
                .statusCode(Integer.parseInt(expectedStatus))
                .body("user.email", equalTo(email))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("Valid login tested successfully");
    }

    @Test(dataProvider = "invalidEmailData", dataProviderClass = DpExcel.class)
    public void invalidEmailLogin(String email, String password, String expectedStatus, String expectedMessage) {
        Response r = loginResponse(email, password)
                .then()
                .statusCode(Integer.parseInt(expectedStatus))
                .body("message[0].value", equalTo(expectedMessage))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("inValid Email login tested successfully");
    }

    @Test(dataProvider = "invalidPasswordData", dataProviderClass = DpExcel.class)
    public void invalidPasswordLogin(String email, String password, String expectedStatus, String expectedMessage) {
        Response r = loginResponse(email, password)
                .then()
                .statusCode(Integer.parseInt(expectedStatus))
                .body("message[0].value", equalTo(expectedMessage))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("inValid password login tested successfully");
    }

    @Test(dataProvider = "emptyData", dataProviderClass = DpExcel.class)
    public void invalidLeaveEmptyFieldLogin(String email, String password, String expectedStatus, String expectedMessage) {
        Response r = loginResponse(email, password)
                .then()
                .statusCode(Integer.parseInt(expectedStatus))
                .body("message[0].value", equalTo(expectedMessage))
                .extract()
                .response();

        r.prettyPrint();
        System.out.println("inValid login leaving field empty tested successfully");
    }
}
