package io.github.kenbme;

import io.github.kenbme.entities.Client;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

@QuarkusTest
class AuthResourceTest {

  @BeforeEach
  @Transactional
  void setup() {
    Client.deleteAll();
  }

  @Test
  void testRegisterEndpoint() {
    var count = Client.count();

    given()
        .contentType(ContentType.JSON)
        .body(Map.of("username", "John",
            "email", "john@gmail.com",
            "password", "123"))
        .when().post("/api/register")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("message", is("Registered successfully"));

    assertEquals(count + 1, Client.count());
  }

  @Test
  void testAlreadyRegisteredEmail() {
    given()
        .contentType(ContentType.JSON)
        .body(Map.of("username", "John",
            "email", "john@gmail.com",
            "password", "123"))
        .when().post("/api/register")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("message", is("Registered successfully"));

    given()
        .contentType(ContentType.JSON)
        .body(Map.of("username", "John",
            "email", "john@gmail.com",
            "password", "123"))
        .when().post("/api/register")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(409)
        .body("error", is("Email already registered"));
  }

  @Test
  void testLoginEndpoint() {
    given()
        .contentType(ContentType.JSON)
        .body(Map.of("username", "John",
            "email", "john@gmail.com",
            "password", "123"))
        .when().post("/api/register")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("message", is("Registered successfully"));

    given()
        .contentType(ContentType.JSON)
        .body(Map.of("email", "john@gmail.com",
            "password", "123"))
        .when().post("/api/login")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("token", notNullValue());
  }

  @Test
  void testCustomerLoginEndpoint() {
    given()
        .contentType(ContentType.JSON)
        .body(Map.of("username", "John",
            "email", "john@gmail.com",
            "password", "123"))
        .when().post("/api/register")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("message", is("Registered successfully"));

    var response = given()
        .contentType(ContentType.JSON)
        .body(Map.of("email", "john@gmail.com",
            "password", "123"))
        .when().post("/api/login")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(200)
        .body("token", notNullValue())
        .extract().response();

    var token = response.jsonPath().getString("token");

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/customer/hello")
        .then()
        .statusCode(200);

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/admin/hello")
        .then()
        .statusCode(403);
  }

}