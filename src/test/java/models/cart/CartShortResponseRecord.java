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
    public boolean isEmpty() {
        return positions == null || positions.isEmpty();
    }

    public int getActualTotalCount() {
        return totalCount != null ? totalCount : 0;
    }

    public boolean containsProductId(String productId) {
        if (positions == null) return false;
        return positions.stream()
                .anyMatch(item -> item.productId().equals(productId));
    }

    public CartItemRecord findProductById(String productId) {
        if (positions == null) return null;
        return positions.stream()
                .filter(item -> item.productId().equals(productId))
                .findFirst()
                .orElse(null);
    }
}
