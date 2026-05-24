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
) {}
