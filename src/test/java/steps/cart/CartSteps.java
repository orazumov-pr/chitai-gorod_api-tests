package steps.cart;

import data.EndpointsData;
import models.cart.CartItemRecord;
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

    /**
     * Проверка, что товар присутствует в корзине
     */
    public void checkCartShortProductId(CartShortResponseRecord response, SearchProductRecord product) {
        Assertions.assertFalse(response.isEmpty(), "Корзина не должна быть пустой");
        boolean found = response.containsProductId(product.id());
        Assertions.assertTrue(found,
                String.format("Продукт с ID '%s' не найден в корзине. Все ID в корзине: %s",
                        product.id(), response.getAllProductIds()));
    }

    /**
     * Проверка наличия товара по ID
     */
    public void checkCartContainsProduct(CartShortResponseRecord response, String expectedProductId) {
        Assertions.assertTrue(response.containsProductId(expectedProductId),
                String.format("Корзина не содержит товар с ID: %s. Все ID в корзине: %s",
                        expectedProductId, response.getAllProductIds()));
    }

    /**
     * Проверка количества товаров в корзине
     */
    public void checkCartQuantity(CartShortResponseRecord response, int expectedCount) {
        int actualCount = response.getActualTotalCount();
        Assertions.assertEquals(expectedCount, actualCount,
                String.format("Количество товаров в корзине не совпадает. Ожидалось: %d, Фактически: %d",
                        expectedCount, actualCount));
    }

    /**
     * Проверка, что корзина пуста
     */
    public void checkCartShortEmpty(CartShortResponseRecord response) {
        Assertions.assertTrue(response.isEmpty(),
                String.format("Корзина должна быть пустой, но содержит %d товаров: %s",
                        response.getUniqueItemsCount(), response.getAllProductIds()));
    }

    /**
     * Проверка общей суммы корзины
     */
    public void verifyCartTotalPrice(CartShortResponseRecord response) {
        boolean isValid = response.isTotalPriceValid();
        Assertions.assertTrue(isValid,
                String.format("Общая стоимость корзины рассчитана неверно. Ожидалось: %.2f, Фактически: %.2f",
                        response.calculateTotalPrice(), response.getActualTotalPrice()));
    }

    /**
     * Проверка количества конкретного товара
     */
    public void checkItemQuantity(CartShortResponseRecord response, String productId, int expectedQuantity) {
        int actualQuantity = response.getProductQuantity(productId);
        Assertions.assertEquals(expectedQuantity, actualQuantity,
                String.format("Количество товара с ID '%s' не совпадает. Ожидалось: %d, Фактически: %d",
                        productId, expectedQuantity, actualQuantity));
    }

    /**
     * Проверка цены конкретного товара в корзине
     */
    public void checkItemPrice(CartShortResponseRecord response, String productId, double expectedPrice) {
        CartItemRecord item = response.findProductById(productId);
        Assertions.assertNotNull(item, "Товар с ID " + productId + " не найден в корзине");
        double actualPrice = item.getPrice() != null ? item.getPrice() : 0.0;
        Assertions.assertEquals(expectedPrice, actualPrice, 0.01,
                String.format("Цена товара с ID '%s' не совпадает. Ожидалось: %.2f, Фактически: %.2f",
                        productId, expectedPrice, actualPrice));
    }

    /**
     * Проверка цены со скидкой конкретного товара
     */
    public void checkItemDiscountPrice(CartShortResponseRecord response, String productId, double expectedDiscountPrice) {
        CartItemRecord item = response.findProductById(productId);
        Assertions.assertNotNull(item, "Товар с ID " + productId + " не найден в корзине");
        double actualDiscountPrice = item.getDiscountPrice() != null ? item.getDiscountPrice() : 0.0;
        Assertions.assertEquals(expectedDiscountPrice, actualDiscountPrice, 0.01,
                String.format("Цена со скидкой товара с ID '%s' не совпадает. Ожидалось: %.2f, Фактически: %.2f",
                        productId, expectedDiscountPrice, actualDiscountPrice));
    }

    /**
     * Проверка суммы конкретного товара в корзине
     */
    public void checkItemTotalPrice(CartShortResponseRecord response, String productId, double expectedPrice) {
        double actualPrice = response.getProductTotalPrice(productId);
        Assertions.assertEquals(expectedPrice, actualPrice, 0.01,
                String.format("Стоимость товара с ID '%s' не совпадает. Ожидалось: %.2f, Фактически: %.2f",
                        productId, expectedPrice, actualPrice));
    }

    /**
     * Проверка наличия скидки на товар
     */
    public void checkItemHasDiscount(CartShortResponseRecord response, String productId, boolean shouldHaveDiscount) {
        CartItemRecord item = response.findProductById(productId);
        Assertions.assertNotNull(item, "Товар с ID " + productId + " не найден в корзине");
        Assertions.assertEquals(shouldHaveDiscount, item.hasDiscount(),
                String.format("Товар с ID '%s' %s иметь скидку",
                        productId, shouldHaveDiscount ? "должен" : "не должен"));
    }

    /**
     * Получение количества уникальных товаров
     */
    public void checkUniqueItemsCount(CartShortResponseRecord response, int expectedCount) {
        int actualCount = response.getUniqueItemsCount();
        Assertions.assertEquals(expectedCount, actualCount,
                String.format("Количество уникальных товаров не совпадает. Ожидалось: %d, Фактически: %d",
                        expectedCount, actualCount));
    }

    /**
     * Получение первого товара из корзины
     */
    public CartItemRecord getFirstItem(CartShortResponseRecord response) {
        Assertions.assertFalse(response.isEmpty(), "Корзина пуста, невозможно получить первый товар");
        return response.getPositions().get(0);
    }

    /**
     * Логирование содержимого корзины (для отладки)
     */
    public void logCartContents(CartShortResponseRecord response) {
        if (response.isEmpty()) {
            System.out.println("📦 Корзина пуста");
        } else {
            System.out.println("=== 📦 СОДЕРЖИМОЕ КОРЗИНЫ ===");
            System.out.printf("📊 Всего товаров: %d, Уникальных позиций: %d%n",
                    response.getActualTotalCount(), response.getUniqueItemsCount());
            System.out.printf("💰 Общая сумма: %.2f руб.%n", response.getActualTotalPrice());
            if (response.getTotalDiscountPrice() != null && response.getTotalDiscountPrice() > 0) {
                System.out.printf("🏷️ Сумма со скидкой: %.2f руб.%n", response.getTotalDiscountPrice());
            }
            System.out.println("📋 Список товаров:");
            response.getPositions().forEach(item -> {
                String discountInfo = "";
                if (item.hasDiscount()) {
                    discountInfo = String.format(" (скидка %.0f%%, экономия %.2f руб.)",
                            item.getDiscountPercent(), item.getDiscountAmount());
                }
                System.out.printf("  • %s (ID: %s): %d шт. x %.2f руб. = %.2f руб.%s%n",
                        item.getTitle() != null ? item.getTitle() : "Без названия",
                        item.getProductId(),
                        item.getQuantity(),
                        item.getEffectivePrice(),
                        item.getTotalPrice(),
                        discountInfo);
            });
            System.out.println("=================================");
        }
    }
}