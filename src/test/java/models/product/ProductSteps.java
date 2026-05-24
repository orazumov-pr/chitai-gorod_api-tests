package models.product;

import data.EndpointsData;
import models.cart.AddToCartRequestRecord;
import models.product.ProductErrorResponseRecord;
import models.search.SearchProductRecord;
import org.junit.jupiter.api.Assertions;
import steps.BaseSteps;

public class ProductSteps extends BaseSteps {

    public void addItemById(SearchProductRecord product, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(product.id());
        executePost(EndpointsData.PRODUCT, request);
    }

    public void addItemWithQuantity(String productId, int quantity) {
        AddToCartRequestRecord request = AddToCartRequestRecord.of(productId, quantity);
        executePost(EndpointsData.PRODUCT, request);
    }

    public ProductErrorResponseRecord addErrItemById(String errorId, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.singleItem(errorId);
        return executePost(EndpointsData.PRODUCT, request)
                .as(ProductErrorResponseRecord.class);
    }

    public void checkErrMsg(ProductErrorResponseRecord response) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertNotNull(response.error(), "Сообщение об ошибке не должно быть null");
        Assertions.assertTrue(response.isNotFoundError(),
                "Сообщение об ошибке не соответствует ожидаемому: " + response.error());
    }

    public void checkErrorStatusCode(ProductErrorResponseRecord response, int expectedStatusCode) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertEquals(expectedStatusCode, response.statusCode(),
                "Status code не совпадает");
    }
}

