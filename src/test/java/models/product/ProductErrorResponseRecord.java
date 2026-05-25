package models.product;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductErrorResponseRecord(
        @JsonProperty("error") String error,
        @JsonProperty("statusCode") Integer statusCode,
        @JsonProperty("message") String message,
        @JsonProperty("code") String code,
        @JsonProperty("details") String details
) {

    // ============ ГЕТТЕРЫ ДЛЯ СОВМЕСТИМОСТИ ============

    public String getError() {
        return error;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }

    // ============ МЕТОДЫ-ПОМОЩНИКИ ДЛЯ ПРОВЕРОК ============

    /**
     * Проверка, что ошибка связана с отсутствием товара
     * @return true если товар не найден
     */
    public boolean isNotFoundError() {
        return error != null && (error.contains("не найден") || error.contains("not found"));
    }

    /**
     * Проверка, что ошибка связана с отсутствием авторизации
     * @return true если ошибка авторизации
     */
    public boolean isUnauthorizedError() {
        return statusCode != null && statusCode == 401;
    }

    /**
     * Проверка, что ошибка связана с неверным запросом
     * @return true если ошибка валидации
     */
    public boolean isBadRequestError() {
        return statusCode != null && statusCode == 400;
    }

    /**
     * Проверка, что ошибка связана с недоступностью товара
     * @return true если товар недоступен
     */
    public boolean isProductUnavailableError() {
        return error != null && (error.contains("недоступен") || error.contains("unavailable"));
    }

    /**
     * Проверка, что ошибка содержит определенный текст
     * @param text текст для поиска
     * @return true если текст найден
     */
    public boolean containsErrorText(String text) {
        if (error == null) return false;
        return error.toLowerCase().contains(text.toLowerCase());
    }

    /**
     * Получение полного сообщения об ошибке
     * @return сообщение об ошибке с деталями
     */
    public String getFullErrorMessage() {
        StringBuilder sb = new StringBuilder();
        if (error != null) sb.append("Error: ").append(error);
        if (message != null) sb.append("; Message: ").append(message);
        if (code != null) sb.append("; Code: ").append(code);
        if (details != null) sb.append("; Details: ").append(details);
        if (statusCode != null) sb.append("; Status: ").append(statusCode);
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("ProductErrorResponseRecord{error='%s', statusCode=%d, message='%s', code='%s'}",
                error != null ? error : "null",
                statusCode != null ? statusCode : 0,
                message != null ? message : "null",
                code != null ? code : "null");
    }
}
