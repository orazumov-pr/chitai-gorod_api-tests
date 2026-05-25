package tests;

import helpers.AuthToken;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public class TestBase {

    protected String token;

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://web-gate.chitai-gorod.ru";
        RestAssured.basePath = "/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void setUp() {
        AuthToken.clearToken();
        token = AuthToken.getAccessToken();  // Инициализируем токен перед каждым тестом
    }

    @AfterEach
    public void tearDown() {
        // Очистка тестовых данных после каждого теста при необходимости
        AuthToken.clearToken();
    }
}
