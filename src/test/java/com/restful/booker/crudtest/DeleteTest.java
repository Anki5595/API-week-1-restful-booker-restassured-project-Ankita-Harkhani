package com.restful.booker.crudtest;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteTest extends TestBase {
    static int id = Integer.parseInt(TestUtils.getRandomValue());
    static String token;

    @BeforeClass
    public void inIt() {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");
        token = given()
                .header("Content-Type", "application/json")
                .when()
                .body(authPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().statusCode(200).extract().path("token");
    }

    @Test
    public void deleteSingleBookingID() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .when().delete("https://restful-booker.herokuapp.com/booking/"+id);
        response.then().statusCode(201);
    }
}
