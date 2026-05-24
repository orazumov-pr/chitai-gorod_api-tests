package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://web-gate.chitai-gorod.ru";
        RestAssured.basePath = "/api";
    }
}
