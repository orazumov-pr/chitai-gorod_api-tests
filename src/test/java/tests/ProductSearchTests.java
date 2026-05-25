package tests;

import data.ProductsData;
import models.search.SearchResponseRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.search.SearchSteps;

import static io.qameta.allure.Allure.step;


@DisplayName("Тесты поиска товаров")
public class ProductSearchTests extends TestBase {

    private final ProductsData data = new ProductsData();
    private final SearchSteps searchSteps = new SearchSteps();

    @Test
    @DisplayName("TC-08: Позитивный тест - Поиск по названию книги")
    void searchByBookTitleTest() {
        SearchResponseRecord response = step("Поиск книги по названию", () ->
                searchSteps.searchItem(data.getBookTitle(), token));

        step("Проверка, что результаты найдены", () -> {
            Assertions.assertNotNull(response.getData(), "Данные поиска не должны быть null");
            Assertions.assertTrue(response.getTotalCount() > 0, "Не найдено результатов по запросу");
        });

        step("Проверка соответствия найденных результатов", () -> {
            boolean hasRelevantResults = response.getData().stream()
                    .anyMatch(product -> product.getTitle().toLowerCase().contains("онегин"));
            Assertions.assertTrue(hasRelevantResults, "Результаты не соответствуют поисковому запросу");
        });
    }

    @Test
    @DisplayName("TC-09: Позитивный тест - Поиск с пагинацией")
    void searchWithPaginationTest() {
        step("Поиск с параметрами пагинации", () -> {
            SearchResponseRecord page1 = searchSteps.searchWithPagination(data.getAuthor(), 1, 5);
            SearchResponseRecord page2 = searchSteps.searchWithPagination(data.getAuthor(), 2, 5);

            step("Проверка количества элементов на странице", () -> {
                Assertions.assertTrue(page1.getData().size() <= 5,
                        "На первой странице больше 5 элементов");
            });

            step("Проверка, что страницы разные", () -> {
                if (!page2.getData().isEmpty()) {
                    Assertions.assertNotEquals(page1.getData().get(0).getId(),
                            page2.getData().get(0).getId(),
                            "Первые элементы страниц не должны совпадать");
                }
            });
        });
    }

    @Test
    @DisplayName("TC-10: Негативный тест - Поиск по пустому запросу")
    void searchWithEmptyQueryTest() {
        step("Поиск с пустым параметром", () -> {
            SearchResponseRecord response = searchSteps.searchItem("", token);
            step("Проверка, что API вернул корректный ответ", () -> {
                Assertions.assertNotNull(response, "Ответ не должен быть null");
            });
        });
    }
}
