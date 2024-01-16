package com.restful.booker.crudtest;

import com.restful.booker.testbase.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetTest extends TestBase {
    @Test
    public void test002_getAllData() {
        Response response = given()
                .when()
                .get();
        response.then().statusCode(200)
                .log().all();
        response.prettyPrint();
    }
}

