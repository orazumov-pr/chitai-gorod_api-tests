package steps.product;

import data.EndpointsData;
import models.cart.AddToCartRequestRecord;
import models.product.ProductErrorResponseRecord;
import models.search.SearchProductRecord;
import org.junit.jupiter.api.Assertions;
import steps.BaseSteps;

public class ProductSteps extends BaseSteps {

    public void addItemById(SearchProductRecord product, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.builder()
                .id(product.getId())
                .quantity(1)
                .build();
        executePost(EndpointsData.PRODUCT, request);
    }

    public void addItemWithQuantity(String productId, int quantity) {
        AddToCartRequestRecord request = AddToCartRequestRecord.builder()
                .id(productId)
                .quantity(quantity)
                .build();
        executePost(EndpointsData.PRODUCT, request);
    }

    public ProductErrorResponseRecord addErrItemById(String errorId, String token) {
        AddToCartRequestRecord request = AddToCartRequestRecord.builder()
                .id(errorId)
                .quantity(1)
                .build();
        return executePost(EndpointsData.PRODUCT, request)
                .as(ProductErrorResponseRecord.class);
    }

    public void checkErrMsg(ProductErrorResponseRecord response) {
        Assertions.assertNotNull(response, "Ответ с ошибкой не должен быть null");
        Assertions.assertNotNull(response.getError(), "Сообщение об ошибке не должно быть null");
        Assertions.assertTrue(response.getError().contains("не найден") || response.getError().contains("недоступен"),
                "Сообщение об ошибке не соответствует ожидаемому: " + response.getError());
    }
}

