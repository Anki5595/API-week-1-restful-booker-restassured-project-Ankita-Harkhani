package com.restful.booker.crudtest;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class BookingCrudTest extends TestBase {

    static String token;
    static String firstName = "Anki" + TestUtils.getRandomValue();
    static String lastName = "Patel" + TestUtils.getRandomValue();
    static int totalPrice = Integer.parseInt(TestUtils.getRandomValue());
    static Boolean depositPaid = true;
    static String additionalNeed = TestUtils.getRandomValue();
    static String id;
    static String username = "admin";
    static String password = "password123";

    @Test
    public void test001_PostAuthCreation() {
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(username);
        authPojo.setPassword(password);
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(authPojo)
                .post(baseURI + "/auth");
        response.then().log().all().statusCode(200);
    }

    @Test
    public void test002_getAllData() {
        Response response = given()
                .when()
                .get();
        response.then().statusCode(200)
                .log().all();
        response.prettyPrint();
    }

    @Test
    public void test003_PostCreateBooking() {
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

        //Use response.jsonPath().getString("bookingid") to extract the bookingid
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post(baseURI + basePath);
        //Extracting the bookingid from the response and assigning it to the 'id' variable
        id = response.jsonPath().getString("bookingid");
        response.then().statusCode(200);
    }

    @Test
    public void test005_UpdateFullBooking() {
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
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(bookingPojo)
                .when().put(baseURI + basePath + id);
        id = response.jsonPath().getString("bookingid");
        response.then().statusCode(200);
    }

    @Test
    public void test004_UpdatePartialBooking() {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setLastname("NewLastName");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(bookingPojo)
                .when().patch(baseURI + basePath + id);

        response.then().statusCode(200);
    }

    @Test
    public void test006_DeleteBooking() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .when().delete(baseURI + basePath + id);

        response.then().statusCode(201);
    }
}