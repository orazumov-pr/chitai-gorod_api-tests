package models.product;

import data.EndpointsData;
import models.cart.AddToCartRequestRecord;
import models.search.SearchProductRecord;
import org.junit.jupiter.api.Assertions;
import steps.BaseSteps;

public class ProductSteps extends BaseSteps {

    /**
     * Добавление товара в корзину по объекту SearchProductRecord
     * @param product объект товара из поиска
     * @param token токен авторизации
     */
    public void addItemById(SearchProductRecord product, String token) {
        // Используем factory метод вместо builder
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(product.id());
        executePost(EndpointsData.PRODUCT, request);
    }

    /**
     * Добавление товара в корзину по ID
     * @param productId ID товара
     * @param token токен авторизации
     */
    public void addItemById(String productId, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(productId);
        executePost(EndpointsData.PRODUCT, request);
    }

    /**
     * Добавление товара с указанным количеством
     * @param productId ID товара
     * @param quantity количество
     */
    public void addItemWithQuantity(String productId, int quantity) {
        AddToCartRequestRecord request = AddToCartRequestRecord.of(productId, quantity);
        executePost(EndpointsData.PRODUCT, request);
    }

    /**
     * Добавление товара с валидацией количества
     * @param productId ID товара
     * @param quantity количество (будет автоматически приведено к диапазону 1-99)
     */
    public void addItemWithValidatedQuantity(String productId, int quantity) {
        AddToCartRequestRecord request = AddToCartRequestRecord.createValidated(productId, quantity);
        executePost(EndpointsData.PRODUCT, request);
    }

    /**
     * Негативный тест - добавление товара с ошибкой
     * @param errorId несуществующий ID товара
     * @param token токен авторизации
     * @return объект с ошибкой
     */
    public ProductErrorResponseRecord addErrItemById(String errorId, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(errorId);
        return executePost(EndpointsData.PRODUCT, request)
                .as(ProductErrorResponseRecord.class);
    }

    /**
     * Проверка сообщения об ошибке
     * @param response объект ответа с ошибкой
     */
    public void checkErrMsg(ProductErrorResponseRecord response) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertNotNull(response.getError(), "Сообщение об ошибке не должно быть null");
        Assertions.assertTrue(response.isNotFoundError() || response.isProductUnavailableError(),
                "Сообщение об ошибке не соответствует ожидаемому. Фактическое: " + response.getFullErrorMessage());
    }

    /**
     * Проверка статус кода ошибки
     * @param response объект ответа с ошибкой
     * @param expectedStatusCode ожидаемый статус код
     */
    public void checkErrorStatusCode(ProductErrorResponseRecord response, int expectedStatusCode) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode(),
                String.format("Status code не совпадает. Ожидался: %d, Фактический: %d",
                        expectedStatusCode, response.getStatusCode()));
    }

    /**
     * Проверка, что ошибка содержит определенный текст
     * @param response объект ответа с ошибкой
     * @param expectedText ожидаемый текст в ошибке
     */
    public void checkErrorContainsText(ProductErrorResponseRecord response, String expectedText) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertTrue(response.containsErrorText(expectedText),
                String.format("Ошибка должна содержать текст '%s'. Фактическая ошибка: '%s'",
                        expectedText, response.getError()));
    }

    /**
     * Получение полного сообщения об ошибке для логирования
     * @param response объект ответа с ошибкой
     * @return полное сообщение об ошибке
     */
    public String getFullErrorMessage(ProductErrorResponseRecord response) {
        return response != null ? response.getFullErrorMessage() : "Response is null";
    }
}
