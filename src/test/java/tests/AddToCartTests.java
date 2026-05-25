package tests;


import data.ProductsData;
import models.cart.CartShortResponseRecord;
import models.search.SearchResponseRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.cart.CartSteps;
import steps.product.ProductSteps;
import steps.search.SearchSteps;

import static io.qameta.allure.Allure.step;


@DisplayName("Тесты добавления товаров в корзину")
public class AddToCartTests extends TestBase {

    private final ProductsData data = new ProductsData();
    private final SearchSteps searchSteps = new SearchSteps();
    private final ProductSteps productSteps = new ProductSteps();
    private final CartSteps cartSteps = new CartSteps();

    @Test
    @DisplayName("TC-01: Позитивный тест - Добавление одного товара в пустую корзину")
    void addSingleProductToCartTest() {
        step("Очищаем корзину перед тестом", () -> cartSteps.deleteAllCart(token));

        SearchResponseRecord searchResult = step("Поиск товара по автору", () ->
                searchSteps.searchItem(data.getAuthor(), token));

        step("Проверка что результаты поиска есть", () ->
                org.junit.jupiter.api.Assertions.assertTrue(searchResult.hasResults(), "Нет результатов поиска"));

        // Теперь используется правильный метод без builder
        step("Добавление первого найденного товара в корзину", () ->
                productSteps.addItemById(searchResult.getFirstResult(), token));

        CartShortResponseRecord cart = step("Получение содержимого корзины", () ->
                cartSteps.getCartShort(token));

        step("Проверка, что в корзине 1 товар", () ->
                cartSteps.checkCartQuantity(cart, 1));

        step("Проверка соответствия ID добавленного товара", () ->
                cartSteps.checkCartShortProductId(cart, searchResult.getFirstResult()));

        step("Проверка корректности расчета суммы", () ->
                cartSteps.verifyCartTotalPrice(cart));
    }

    @Test
    @DisplayName("TC-02: Позитивный тест - Добавление нескольких одинаковых товаров")
    void addMultipleQuantityTest() {
        step("Очищаем корзину перед тестом", () -> cartSteps.deleteAllCart(token));

        step("Добавление товара в количестве 3 штук", () ->
                productSteps.addItemWithQuantity(data.getValidProductId(), 3));

        CartShortResponseRecord cart = step("Получение содержимого корзины", () ->
                cartSteps.getCartShort(token));

        step("Проверка количества товаров в корзине", () ->
                cartSteps.checkCartQuantity(cart, 3));

        step("Проверка количества конкретного товара", () ->
                cartSteps.checkItemQuantity(cart, data.getValidProductId(), 3));
    }

    @Test
    @DisplayName("TC-04: Тест с валидацией количества - добавление 0 товаров")
    void addProductWithZeroQuantityTest() {
        step("Очищаем корзину перед тестом", () -> cartSteps.deleteAllCart(token));

        step("Попытка добавить товар с количеством 0", () ->
                productSteps.addItemWithQuantity(data.getValidProductId(), 0));

        CartShortResponseRecord cart = step("Проверка, что товар не был добавлен", () ->
                cartSteps.getCartShort(token));

        step("Подтверждение, что корзина пуста", () ->
                cartSteps.checkCartShortEmpty(cart));
    }
}