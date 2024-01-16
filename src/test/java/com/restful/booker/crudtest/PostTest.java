package com.restful.booker.crudtest;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostTest extends TestBase {

    static String firstName = "Anki" + TestUtils.getRandomValue();
    static String lastName = "Patel" + TestUtils.getRandomValue();
    static int totalPrice = Integer.parseInt(TestUtils.getRandomValue());
    static Boolean depositPaid = true;
    static String additionalNeed = TestUtils.getRandomValue();
    static String id;
    static String username = "admin";
    static String password = "password123";


    @Test
    public void createToken() {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(username);
        authPojo.setPassword(password);
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(authPojo)
                .post("https://restful-booker.herokuapp.com/auth");
        response.then().statusCode(200);
    }

    @Test
    public void createBooking() {
        BookingPojo.BookingDates date = new BookingPojo.BookingDates();
        date.setCheckin("2023-07-01");
        date.setCheckout("2023-07-10");
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstName);
        bookingPojo.setLastname(lastName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(depositPaid);
        bookingPojo.setBookingdates(date);
        bookingPojo.setAdditionalneeds(additionalNeed);
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post("https://restful-booker.herokuapp.com/booking");
        response.then().statusCode(200);
    }

}

