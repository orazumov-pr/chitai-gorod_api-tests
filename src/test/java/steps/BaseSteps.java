package steps;

import helpers.AuthToken;
import io.restassured.response.Response;
import specs.Specification;

import static io.restassured.RestAssured.given;

public abstract class BaseSteps {
    protected String token;

    protected BaseSteps() {
        this.token = AuthToken.getAccessToken();
    }

    protected Response executeGet(String endpoint) {
        return given()
                .spec(Specification.requestSpec(token))
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response executePost(String endpoint, Object body) {
        return given()
                .spec(Specification.requestSpec(token))
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response executePut(String endpoint, Object body) {
        return given()
                .spec(Specification.requestSpec(token))
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    protected Response executeDelete(String endpoint) {
        return given()
                .spec(Specification.requestSpec(token))
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}

