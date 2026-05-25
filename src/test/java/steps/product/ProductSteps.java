package steps.product;

import data.EndpointsData;
import models.cart.AddToCartRequestRecord;
import models.product.ProductErrorResponseRecord;
import models.search.SearchProductRecord;
import org.junit.jupiter.api.Assertions;
import steps.BaseSteps;

public class ProductSteps extends BaseSteps {

    public void addItemById(SearchProductRecord product, String token) {
        // Используем factory метод вместо builder
        // У Records нет builder(), используем id() вместо getId()
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(product.id());
        executePost(EndpointsData.PRODUCT, request);
    }

    public void addItemById(String productId, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(productId);
        executePost(EndpointsData.PRODUCT, request);
    }

    public void addItemWithQuantity(String productId, int quantity) {
        AddToCartRequestRecord request = AddToCartRequestRecord.of(productId, quantity);
        executePost(EndpointsData.PRODUCT, request);
    }

    public void addItemWithValidatedQuantity(String productId, int quantity) {
        AddToCartRequestRecord request = AddToCartRequestRecord.createValidated(productId, quantity);
        executePost(EndpointsData.PRODUCT, request);
    }

    public ProductErrorResponseRecord addErrItemById(String errorId, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(errorId);
        return executePost(EndpointsData.PRODUCT, request)
                .as(ProductErrorResponseRecord.class);
    }

    public void checkErrMsg(ProductErrorResponseRecord response) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertNotNull(response.getError(), "Сообщение об ошибке не должно быть null");
        Assertions.assertTrue(response.isNotFoundError() || response.isProductUnavailableError(),
                "Сообщение об ошибке не соответствует ожидаемому. Фактическое: " + response.getFullErrorMessage());
    }

    public void checkErrorStatusCode(ProductErrorResponseRecord response, int expectedStatusCode) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode(),
                String.format("Status code не совпадает. Ожидался: %d, Фактический: %d",
                        expectedStatusCode, response.getStatusCode()));
    }

    public void checkErrorContainsText(ProductErrorResponseRecord response, String expectedText) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertTrue(response.containsErrorText(expectedText),
                String.format("Ошибка должна содержать текст '%s'. Фактическая ошибка: '%s'",
                        expectedText, response.getError()));
    }

    public String getFullErrorMessage(ProductErrorResponseRecord response) {
        return response != null ? response.getFullErrorMessage() : "Response is null";
    }
}