package specs;

import helpers.CustomApiListener;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class Specification {

    public static RequestSpecification requestSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri("https://web-gate.chitai-gorod.ru")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("User-Agent", "ChitaiGorod/1.0 (API-Tests)")
                .addFilter(new AllureRestAssured())
                .addFilter(new CustomApiListener())
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification requestSpecWithoutAuth() {
        return new RequestSpecBuilder()
                .setBaseUri("https://web-gate.chitai-gorod.ru")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("User-Agent", "ChitaiGorod/1.0 (API-Tests)")
                .addFilter(new AllureRestAssured())
                .addFilter(new CustomApiListener())
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification successResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine(containsString("OK"))
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification createdResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectStatusLine(containsString("Created"))
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification badRequestResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectStatusLine(containsString("Bad Request"))
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification unauthorizedResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine(containsString("Unauthorized"))
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification notFoundResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectStatusLine(containsString("Not Found"))
                .log(LogDetail.ALL)
                .build();
    }
}
