package data;

public class ProductsData {
    // Позитивные тестовые данные
    private final String bookTitle = "Евгений Онегин";
    private final String author = "Пушкин";
    private final String validProductId = "1005902"; // ID существующей книги
    private final String validProductId2 = "1008345"; // Еще одна книга
    private final String validProductId3 = "1002646"; // Третья книга

    // Негативные тестовые данные
    private final String errorId = "0"; // Несуществующий ID
    private final String invalidIdFormat = "abc123"; // Неверный формат
    private final String emptyId = "";
    private final String productOutOfStock = "1000001"; // Товар не в наличии

    // Количество товаров
    private final int quantityOne = 1;
    private final int quantityTwo = 2;
    private final int quantityMax = 99;

    // ============ ГЕТТЕРЫ ============

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getValidProductId() {
        return validProductId;
    }

    public String getValidProductId2() {
        return validProductId2;
    }

    public String getValidProductId3() {
        return validProductId3;
    }

    public String getErrorId() {
        return errorId;
    }

    public String getInvalidIdFormat() {
        return invalidIdFormat;
    }

    public String getEmptyId() {
        return emptyId;
    }

    public String getProductOutOfStock() {
        return productOutOfStock;
    }

    public int getQuantityOne() {
        return quantityOne;
    }

    public int getQuantityTwo() {
        return quantityTwo;
    }

    public int getQuantityMax() {
        return quantityMax;
    }
}