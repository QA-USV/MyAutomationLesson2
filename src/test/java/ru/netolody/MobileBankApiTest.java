package ru.netolody;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

class MobileBankApiTest {
    @Test
    void shouldReturnDemoAccounts() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    void shouldReturnDemoAccounts1() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body(matchesJsonSchemaInClasspath("accounts.schema.json"))
        ;
    }

    @Test
    void shouldReturnDemoAccounts2() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("", hasSize(3))
        ;
    }

    @Test
    void shouldReturnDemoAccounts3() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].currency", equalTo("RUB"))
        ;
    }

    @Test
    void shouldReturnDemoAccounts4() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[1].currency", equalTo("USD"))
        ;
    }

    @Test
    void shouldReturnDemoAccounts5() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].balance", greaterThanOrEqualTo(0))
        ;
    }

    @Test
    void shouldReturnDemoAccounts6() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].number", equalTo("0668"))
        ;
    }

    @Test
    void shouldReturnDemoAccounts7() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[1].number", equalTo("9192"))
        ;
    }

    @Test
    void shouldReturnDemoAccounts8() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].name", equalTo("Текущий счёт"))
        ;
    }


}