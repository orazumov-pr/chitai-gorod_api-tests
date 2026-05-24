package steps.cart;

import data.EndpointsData;
import models.cart.CartShortResponseRecord;
import models.search.SearchProductRecord;
import org.junit.jupiter.api.Assertions;
import steps.BaseSteps;

import java.util.List;

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
        Assertions.assertFalse(response.getPositions().isEmpty(), "Корзина не должна быть пустой");
        boolean found = response.getPositions().stream()
                .anyMatch(item -> item.getProductId().equals(product.getId()));
        Assertions.assertTrue(found, "Продукт с ID " + product.getId() + " не найден в корзине");
    }

    public void checkCartContainsProduct(CartShortResponseRecord response, String expectedProductId) {
        boolean found = response.getPositions().stream()
                .anyMatch(item -> item.getProductId().equals(expectedProductId));
        Assertions.assertTrue(found, "Корзина не содержит товар с ID: " + expectedProductId);
    }

    public void checkCartQuantity(CartShortResponseRecord response, int expectedCount) {
        int actualCount = response.getTotalCount() != null ? response.getTotalCount() : 0;
        Assertions.assertEquals(expectedCount, actualCount,
                String.format("Количество товаров в корзине не совпадает. Ожидалось: %d, Фактически: %d", expectedCount, actualCount));
    }

    public void checkCartShortEmpty(CartShortResponseRecord response) {
        boolean isEmpty = response.getPositions() == null || response.getPositions().isEmpty();
        Assertions.assertTrue(isEmpty, "Корзина должна быть пустой, но найдены товары: " + response.getPositions());
    }

    public void verifyCartTotalPrice(CartShortResponseRecord response) {
        if (response.getPositions() != null && !response.getPositions().isEmpty()) {
            double calculatedTotal = response.getPositions().stream()
                    .mapToDouble(item -> (item.getDiscountPrice() != null ? item.getDiscountPrice() : item.getPrice()) * item.getQuantity())
                    .sum();
            Assertions.assertEquals(calculatedTotal, response.getTotalPrice(), 0.01, "Общая стоимость корзины рассчитана неверно");
        }
    }
}

