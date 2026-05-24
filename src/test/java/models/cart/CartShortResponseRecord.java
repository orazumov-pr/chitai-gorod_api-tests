package models.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartShortResponseRecord(
        @JsonProperty("positions") List<CartItemRecord> positions,
        @JsonProperty("totalCount") Integer totalCount,
        @JsonProperty("totalPrice") Double totalPrice,
        @JsonProperty("totalDiscountPrice") Double totalDiscountPrice
) {

    // ============ ГЕТТЕРЫ ДЛЯ СОВМЕСТИМОСТИ С СУЩЕСТВУЮЩИМИ STEP-КЛАССАМИ ============

    /**
     * Геттер для получения списка позиций в корзине
     * @return список товаров в корзине
     */
    public List<CartItemRecord> getPositions() {
        return positions;
    }

    /**
     * Геттер для получения общего количества товаров в корзине
     * @return количество товаров (может быть null)
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * Геттер для получения общей суммы корзины
     * @return общая сумма (может быть null)
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Геттер для получения общей суммы со скидкой
     * @return общая сумма со скидкой (может быть null)
     */
    public Double getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    // ============ МЕТОДЫ-ПОМОЩНИКИ ДЛЯ ПРОВЕРОК ============

    /**
     * Проверка, пуста ли корзина
     * @return true если корзина пуста
     */
    public boolean isEmpty() {
        return positions == null || positions.isEmpty();
    }

    /**
     * Получение фактического количества товаров (безопасно от null)
     * @return количество товаров (0 если totalCount == null)
     */
    public int getActualTotalCount() {
        return totalCount != null ? totalCount : 0;
    }

    /**
     * Получение фактической суммы (безопасно от null)
     * @return общая сумма (0.0 если totalPrice == null)
     */
    public double getActualTotalPrice() {
        return totalPrice != null ? totalPrice : 0.0;
    }

    /**
     * Проверка наличия товара по ID
     * @param productId ID товара
     * @return true если товар есть в корзине
     */
    public boolean containsProductId(String productId) {
        if (positions == null) return false;
        return positions.stream()
                .anyMatch(item -> item.productId().equals(productId));
    }

    /**
     * Поиск товара в корзине по ID
     * @param productId ID товара
     * @return CartItemRecord или null если не найден
     */
    public CartItemRecord findProductById(String productId) {
        if (positions == null) return null;
        return positions.stream()
                .filter(item -> item.productId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Получение общего количества определенного товара
     * @param productId ID товара
     * @return количество (0 если товар не найден)
     */
    public int getProductQuantity(String productId) {
        CartItemRecord item = findProductById(productId);
        return item != null && item.quantity() != null ? item.quantity() : 0;
    }

    /**
     * Получение общей стоимости определенного товара
     * @param productId ID товара
     * @return стоимость (0.0 если товар не найден)
     */
    public double getProductTotalPrice(String productId) {
        CartItemRecord item = findProductById(productId);
        if (item == null) return 0.0;
        double price = item.discountPrice() != null ? item.discountPrice() :
                (item.price() != null ? item.price() : 0.0);
        int quantity = item.quantity() != null ? item.quantity() : 0;
        return price * quantity;
    }

    /**
     * Подсчет общей суммы корзины на основе позиций
     * @return рассчитанная сумма
     */
    public double calculateTotalPrice() {
        if (positions == null) return 0.0;
        return positions.stream()
                .mapToDouble(item -> {
                    double price = item.discountPrice() != null ? item.discountPrice() :
                            (item.price() != null ? item.price() : 0.0);
                    int quantity = item.quantity() != null ? item.quantity() : 0;
                    return price * quantity;
                })
                .sum();
    }

    /**
     * Валидация соответствия общей суммы корзины сумме позиций
     * @return true если суммы совпадают
     */
    public boolean isTotalPriceValid() {
        if (totalPrice == null && calculateTotalPrice() == 0.0) return true;
        if (totalPrice == null) return false;
        return Math.abs(totalPrice - calculateTotalPrice()) < 0.01;
    }

    /**
     * Получение количества уникальных товаров в корзине
     * @return количество позиций
     */
    public int getUniqueItemsCount() {
        return positions != null ? positions.size() : 0;
    }

    /**
     * Получение списка ID всех товаров в корзине
     * @return список ID
     */
    public List<String> getAllProductIds() {
        if (positions == null) return List.of();
        return positions.stream()
                .map(CartItemRecord::productId)
                .toList();
    }

    @Override
    public String toString() {
        return String.format("CartShortResponseRecord{positions=%s, totalCount=%d, totalPrice=%.2f, totalDiscountPrice=%.2f}",
                positions != null ? positions.size() : 0,
                getActualTotalCount(),
                getActualTotalPrice(),
                totalDiscountPrice != null ? totalDiscountPrice : 0.0);
    }
}
