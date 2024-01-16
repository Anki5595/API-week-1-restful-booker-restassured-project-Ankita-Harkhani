package com.restful.booker.crudtest;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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

    static String firstname = TestUtils.getRandomValue();
    static String lastname = TestUtils.getRandomValue();

    @Test
    public void getAllBookingIds() {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        List<String> bookingdates = new ArrayList<>();
        bookingdates.add("2018-01-01");
        bookingdates.add("2019-01-01");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "application/json")
                .body(bookingPojo)
                .when().get("https://restful-booker.herokuapp.com/booking");
        response.then().log().all().statusCode(200);
    }

    @Test
    public void getBookingIds() {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        List<String> bookingdates = new ArrayList<>();
        bookingdates.add("2018-01-01");
        bookingdates.add("2019-01-01");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "application/json")
                .pathParam("id", 10)
                .body(bookingPojo)
                .when().get("https://restful-booker.herokuapp.com/booking/{id}");
        response.then().log().all().statusCode(200);
    }

    @Test
    public void getBookingWithFirstName() {
        BookingPojo bookingPojo = new BookingPojo();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "application/json")
                .body(bookingPojo)
                .param("firstname", firstname)
                .when().get("https://restful-booker.herokuapp.com/booking");
        response.then().log().all().statusCode(200);
    }

    @Test
    public void patchBookingTest() {
        BookingPojo.BookingDates bookingDates = new BookingPojo.BookingDates();
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setBookingdates(bookingDates);
        bookingPojo.setFirstname("Anki");
        bookingPojo.setLastname("patel");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "application/json")
                .pathParam("id", 10)
                .body(bookingPojo)
                .when().patch("https://restful-booker.herokuapp.com/booking/{id}");
        response.then().log().all().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void putBookingTest() {
        BookingPojo.BookingDates date = new BookingPojo.BookingDates();
        date.setCheckin("2023-06-01");
        date.setCheckout("2023-06-05");
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("mitul");
        bookingPojo.setLastname("Patel");
        bookingPojo.setTotalprice(350);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setBookingdates(date);
        bookingPojo.setAdditionalneeds("Dinner");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .header("Accept", "application/json")
                .pathParam("id", 125)
                .body(bookingPojo)
                .when()
                .put("/booking/{id}");
        response.then().statusCode(404);
        response.prettyPrint();
    }

    @Test
    public void deleteBookingTest() {
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("https://restful-booker.herokuapp.com/booking/4");

        response.then().log().all().statusCode(201);
        response.prettyPrint();
    }

    @Test
    public void healthCheckTest(){
        Response response = given().log().all()
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .get("https://restful-booker.herokuapp.com/ping");
        response.then().statusCode(201);
        response.prettyPrint();
    }

}