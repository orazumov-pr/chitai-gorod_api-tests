package models.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddToCartRequestRecord(
        @JsonProperty("id") String id,
        @JsonProperty("quantity") Integer quantity
) {

    // ============ ГЕТТЕРЫ ДЛЯ СОВМЕСТИМОСТИ ============

    public String getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // ============ ФАКТОРНЫЕ МЕТОДЫ ВМЕСТО BUILDER ============

    /**
     * Создание запроса для добавления одного товара
     * @param id ID товара
     * @return AddToCartRequestRecord
     */
    public static AddToCartRequestRecord singleItem(String id) {
        return new AddToCartRequestRecord(id, 1);
    }

    /**
     * Создание запроса с указанным количеством
     * @param id ID товара
     * @param quantity количество
     * @return AddToCartRequestRecord
     */
    public static AddToCartRequestRecord of(String id, int quantity) {
        return new AddToCartRequestRecord(id, quantity);
    }

    /**
     * Создание запроса с количеством по умолчанию (1)
     * @param id ID товара
     * @return AddToCartRequestRecord
     */
    public static AddToCartRequestRecord create(String id) {
        return new AddToCartRequestRecord(id, 1);
    }

    /**
     * Создание запроса с валидацией количества
     * @param id ID товара
     * @param quantity количество (будет приведено к диапазону 1-99)
     * @return AddToCartRequestRecord
     */
    public static AddToCartRequestRecord createValidated(String id, int quantity) {
        int validQuantity = Math.max(1, Math.min(99, quantity));
        return new AddToCartRequestRecord(id, validQuantity);
    }

    // ============ МЕТОДЫ-ПОМОЩНИКИ ============

    /**
     * Проверка, что количество в допустимом диапазоне
     * @return true если количество от 1 до 99
     */
    public boolean isValidQuantity() {
        return quantity != null && quantity >= 1 && quantity <= 99;
    }

    /**
     * Проверка, что ID не пустой
     * @return true если ID не null и не пустой
     */
    public boolean isValidId() {
        return id != null && !id.trim().isEmpty();
    }

    /**
     * Получение валидированного количества
     * @return количество в диапазоне 1-99
     */
    public int getValidatedQuantity() {
        if (quantity == null) return 1;
        return Math.max(1, Math.min(99, quantity));
    }

    @Override
    public String toString() {
        return String.format("AddToCartRequestRecord{id='%s', quantity=%d}", id, quantity != null ? quantity : 0);
    }
}