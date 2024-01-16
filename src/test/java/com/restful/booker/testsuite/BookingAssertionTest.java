package com.restful.booker.testsuite;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class BookingAssertionTest {
    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        response = given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking/10")
                .then().statusCode(200);
    }

    @Test
    public void test01() {
        response.body("", hasKey("totalprice"));
    }

    @Test
    public void test02(){
        System.out.println("The First name value is : " + response.extract().path("firstname"));
    }

}

