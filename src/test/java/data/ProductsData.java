package data;

public class ProductsData {
    // Позитивные тестовые данные
    public final String bookTitle = "Евгений Онегин";
    public final String author = "Пушкин";
    public final String validProductId = "1005902"; // ID существующей книги
    public final String validProductId2 = "1008345"; // Еще одна книга
    public final String validProductId3 = "1002646"; // Третья книга

    // Негативные тестовые данные
    public final String errorId = "0"; // Несуществующий ID
    public final String invalidIdFormat = "abc123"; // Неверный формат
    public final String emptyId = "";
    public final String productOutOfStock = "1000001"; // Товар не в наличии

    // Количество товаров
    public final int quantityOne = 1;
    public final int quantityTwo = 2;
    public final int quantityMax = 99;
}
