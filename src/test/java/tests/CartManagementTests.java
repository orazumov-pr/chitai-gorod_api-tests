package tests;

import data.ProductsData;
import models.cart.CartShortResponseRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.cart.CartSteps;
import steps.product.ProductSteps;

import static io.qameta.allure.Allure.step;

@DisplayName("Тесты управления корзиной")
public class CartManagementTests extends TestBase {

    private final ProductsData data = new ProductsData();
    private final ProductSteps productSteps = new ProductSteps();
    private final CartSteps cartSteps = new CartSteps();

    @Test
    @DisplayName("TC-05: Позитивный тест - Удаление товара из корзины")
    void deleteProductFromCartTest() {
        step("Подготовка: очистка и добавление товара", () -> {
            cartSteps.deleteAllCart(token);
            productSteps.addItemWithQuantity(data.getValidProductId(), 1);
        });

        CartShortResponseRecord beforeDelete = step("Получение корзины до удаления", () ->
                cartSteps.getCartShort(token));

        step("Проверка, что товар добавлен", () ->
                cartSteps.checkCartQuantity(beforeDelete, 1));

        step("Удаление товара из корзины", () ->
                cartSteps.deleteCartItem(data.getValidProductId()));

        CartShortResponseRecord afterDelete = step("Получение корзины после удаления", () ->
                cartSteps.getCartShort(token));

        step("Проверка, что корзина пуста", () ->
                cartSteps.checkCartShortEmpty(afterDelete));
    }

    @Test
    @DisplayName("TC-06: Позитивный тест - Очистка всей корзины")
    void clearWholeCartTest() {
        step("Подготовка: добавление нескольких товаров", () -> {
            cartSteps.deleteAllCart(token);
            productSteps.addItemWithQuantity(data.getValidProductId(), 2);
            productSteps.addItemWithQuantity(data.getValidProductId2(), 1);
        });

        CartShortResponseRecord beforeClear = step("Проверка корзины до очистки", () ->
                cartSteps.getCartShort(token));

        step("Подтверждение наличия товаров", () ->
                cartSteps.checkCartQuantity(beforeClear, 3));

        step("Очистка всей корзины", () ->
                cartSteps.deleteAllCart(token));

        CartShortResponseRecord afterClear = step("Проверка корзины после очистки", () ->
                cartSteps.getCartShort(token));

        step("Подтверждение, что корзина пуста", () ->
                cartSteps.checkCartShortEmpty(afterClear));
    }

    @Test
    @DisplayName("TC-07: Негативный тест - Удаление несуществующего товара")
    void deleteNonExistentProductTest() {
        step("Очистка корзины", () -> cartSteps.deleteAllCart(token));

        step("Попытка удалить несуществующий товар", () ->
                cartSteps.deleteCartItem("9999999"));

        CartShortResponseRecord cart = step("Проверка, что корзина осталась пустой", () ->
                cartSteps.getCartShort(token));

        step("Подтверждение отсутствия ошибок в структуре корзины", () ->
                cartSteps.checkCartShortEmpty(cart));
    }
}