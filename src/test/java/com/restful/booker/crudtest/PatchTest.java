package com.restful.booker.crudtest;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PatchTest extends TestBase {
    static String token;
    static String additionalNeed = TestUtils.getRandomValue();
    static String username = "admin";
    static String password = "password123";

    @BeforeClass
    public void inIt() {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(username);
        authPojo.setPassword(password);
        token = given()
                .header("Content-Type", "application/json")
                .when()
                .body(authPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().statusCode(200).extract().path("token");
    }

    @Test
    public void updateARecordPartially() {
       BookingPojo patchBookingPojo = new BookingPojo();
        patchBookingPojo.setAdditionalneeds(additionalNeed);
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", 10)
                .body(patchBookingPojo)
                .when().patch("https://restful-booker.herokuapp.com/booking/{id}");
        response.then().log().all().statusCode(200);
    }

}
