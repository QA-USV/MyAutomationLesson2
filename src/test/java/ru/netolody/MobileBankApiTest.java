package ru.netolody;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

class MobileBankApiTest {
    @Test
    void shouldCheckStatusCode() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    void shouldCheckSchema() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body(matchesJsonSchemaInClasspath("accounts.schema.json"))
        ;
    }

    @Test
    void shouldCheckNumberOfObjects() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("", hasSize(3))
        ;
    }

    @Test
    void shouldCheckCurrencyForFirstAccount() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].currency", equalTo("RUR"))
        ;
    }

    @Test
    void shouldCheckCurrencyForSecondAccount() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[1].currency", equalTo("USD"))
        ;
    }

    @Test
    void shouldCheckBalanceForFirstAccount() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].balance", greaterThanOrEqualTo(0))
        ;
    }

    @Test
    void shouldCheckLastFourNumbersForFirstAccount() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].number", stringContainsInOrder ("0668"))
        ;
    }

    @Test
    void shouldCheckLastFourNumbersForSecondAccount() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[1].number", stringContainsInOrder ("9192"))
        ;
    }

    @Test
    void shouldCheckFirstAccountName() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[0].name", equalTo("Текущий счёт"))
        ;
    }

    @Test
    void shouldCheckThirdAccountName() {
        given()
                .baseUri("http://localhost:9999/api/v1")
                .when()
                .get("/demo/accounts")
                .then()
                .body("[2].name", equalTo("Текущий зарплатный счёт"))
        ;
    }
}