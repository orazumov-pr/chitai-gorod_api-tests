package models.cart;

import data.EndpointsData;
import models.cart.CartShortResponseRecord;
import models.search.SearchProductRecord;
import org.junit.jupiter.api.Assertions;
import steps.BaseSteps;

public class CartSteps extends BaseSteps {

    public CartShortResponseRecord getCartShort(String token) {
        return executeGet(EndpointsData.CART_SHORT)
                .as(CartShortResponseRecord.class);
    }

    public void deleteAllCart(String token) {
        executeDelete(EndpointsData.CART);
    }

    public void deleteCartItem(String productId) {
        executeDelete(EndpointsData.DELETE_ITEM + productId);
    }

    public void checkCartShortProductId(CartShortResponseRecord response, SearchProductRecord product) {
        Assertions.assertFalse(response.isEmpty(), "Корзина не должна быть пустой");
        boolean found = response.containsProductId(product.id());
        Assertions.assertTrue(found, "Продукт с ID " + product.id() + " не найден в корзине");
    }

    public void checkCartContainsProduct(CartShortResponseRecord response, String expectedProductId) {
        Assertions.assertTrue(response.containsProductId(expectedProductId),
                "Корзина не содержит товар с ID: " + expectedProductId);
    }

    public void checkCartQuantity(CartShortResponseRecord response, int expectedCount) {
        int actualCount = response.getActualTotalCount();
        Assertions.assertEquals(expectedCount, actualCount,
                String.format("Количество товаров в корзине не совпадает. Ожидалось: %d, Фактически: %d",
                        expectedCount, actualCount));
    }

    public void checkCartShortEmpty(CartShortResponseRecord response) {
        Assertions.assertTrue(response.isEmpty(), "Корзина должна быть пустой");
    }

    public void verifyCartTotalPrice(CartShortResponseRecord response) {
        if (!response.isEmpty()) {
            double calculatedTotal = response.positions().stream()
                    .mapToDouble(item -> (item.discountPrice() != null ? item.discountPrice() : item.price()) * item.quantity())
                    .sum();
            Assertions.assertEquals(calculatedTotal, response.totalPrice(), 0.01,
                    "Общая стоимость корзины рассчитана неверно");
        }
    }

    public void checkItemQuantity(CartShortResponseRecord response, String productId, int expectedQuantity) {
        var item = response.findProductById(productId);
        Assertions.assertNotNull(item, "Товар с ID " + productId + " не найден в корзине");
        Assertions.assertEquals(expectedQuantity, item.quantity(),
                "Количество товара " + productId + " не совпадает");
    }
}
