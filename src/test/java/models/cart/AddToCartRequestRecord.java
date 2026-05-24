package models.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddToCartRequestRecord(
        @JsonProperty("id") String id,
        @JsonProperty("quantity") Integer quantity
) {
    // Factory methods для создания запросов
    public static AddToCartRequestRecord of(String id, int quantity) {
        return new AddToCartRequestRecord(id, quantity);
    }

    public static AddToCartRequestRecord singleItem(String id) {
        return new AddToCartRequestRecord(id, 1);
    }
}
