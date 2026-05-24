package models.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartItemRecord(
        @JsonProperty("id") String id,
        @JsonProperty("productId") String productId,
        @JsonProperty("title") String title,
        @JsonProperty("quantity") Integer quantity,
        @JsonProperty("price") Double price,
        @JsonProperty("discountPrice") Double discountPrice,
        @JsonProperty("available") Boolean available
) {

    // ============ ГЕТТЕРЫ ДЛЯ СОВМЕСТИМОСТИ ============

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public Boolean getAvailable() {
        return available;
    }

    // ============ МЕТОДЫ-ПОМОЩНИКИ ============

    /**
     * Получение актуальной цены (со скидкой или обычной)
     * @return цена
     */
    public double getEffectivePrice() {
        return discountPrice != null ? discountPrice : (price != null ? price : 0.0);
    }

    /**
     * Получение общей стоимости позиции
     * @return цена * количество
     */
    public double getTotalPrice() {
        return getEffectivePrice() * (quantity != null ? quantity : 0);
    }

    /**
     * Проверка наличия товара
     * @return true если товар в наличии
     */
    public boolean isAvailable() {
        return available != null && available;
    }

    /**
     * Проверка, есть ли скидка на товар
     * @return true если есть скидка
     */
    public boolean hasDiscount() {
        return discountPrice != null && price != null && discountPrice < price;
    }

    /**
     * Получение суммы скидки
     * @return размер скидки
     */
    public double getDiscountAmount() {
        if (!hasDiscount()) return 0.0;
        return price - discountPrice;
    }

    /**
     * Получение процента скидки
     * @return процент скидки (0 если нет скидки)
     */
    public double getDiscountPercent() {
        if (!hasDiscount() || price == null || price == 0) return 0.0;
        return ((price - discountPrice) / price) * 100;
    }

    @Override
    public String toString() {
        return String.format("CartItemRecord{id='%s', productId='%s', title='%s', quantity=%d, price=%.2f, discountPrice=%.2f, available=%s}",
                id != null ? id : "null",
                productId != null ? productId : "null",
                title != null ? title : "null",
                quantity != null ? quantity : 0,
                price != null ? price : 0.0,
                discountPrice != null ? discountPrice : 0.0,
                available != null ? available : false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemRecord that)) return false;
        return java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, productId);
    }
}
