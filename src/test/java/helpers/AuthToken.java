package helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.auth.GuestAuthRequestRecord;
import models.auth.AuthTokenResponseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class AuthToken {
    private static final Logger log = LoggerFactory.getLogger(AuthToken.class);
    private static String cachedToken;
    private static long tokenExpiryTime;
    private static final long TOKEN_LIFETIME_MS = 3600000; // 1 час

    public static String getAccessToken() {
        if (isTokenValid()) {
            log.info("Используем кэшированный токен");
            return cachedToken;
        }

        log.info("Получаем новый токен авторизации");
        return generateNewToken();
    }

    private static String generateNewToken() {
        GuestAuthRequestRecord authRequest = GuestAuthRequestRecord.desktopGuest();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .post("https://web-gate.chitai-gorod.ru/api/v1/auth/guest")
                .then()
                .statusCode(200)
                .extract()
                .response();

        AuthTokenResponseRecord tokenResponse = response.as(AuthTokenResponseRecord.class);
        cachedToken = tokenResponse.accessToken();
        tokenExpiryTime = System.currentTimeMillis() + TOKEN_LIFETIME_MS;

        log.info("Токен успешно получен. Истекает через: {} мс", TOKEN_LIFETIME_MS);
        return cachedToken;
    }

    private static boolean isTokenValid() {
        return cachedToken != null && System.currentTimeMillis() < tokenExpiryTime;
    }

    public static void clearToken() {
        cachedToken = null;
        tokenExpiryTime = 0;
        log.info("Токен очищен");
    }
}
