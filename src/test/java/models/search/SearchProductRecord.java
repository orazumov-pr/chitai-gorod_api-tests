package models.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchProductRecord(
        @JsonProperty("id") String id,
        @JsonProperty("title") String title,
        @JsonProperty("author") String author,
        @JsonProperty("price") Double price,
        @JsonProperty("available") Boolean available
) {

    // ============ ГЕТТЕРЫ ДЛЯ СОВМЕСТИМОСТИ ============

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getAvailable() {
        return available;
    }

    // ============ МЕТОДЫ-ПОМОЩНИКИ ============

    /**
     * Проверка доступности товара
     * @return true если товар в наличии
     */
    public boolean isAvailable() {
        return available != null && available;
    }

    /**
     * Получение актуальной цены
     * @return цена (0 если price == null)
     */
    public double getEffectivePrice() {
        return price != null ? price : 0.0;
    }

    /**
     * Проверка, содержит ли название ключевое слово
     * @param keyword ключевое слово
     * @return true если содержит
     */
    public boolean titleContains(String keyword) {
        return title != null && title.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * Проверка, содержит ли автор ключевое слово
     * @param keyword ключевое слово
     * @return true если содержит
     */
    public boolean authorContains(String keyword) {
        return author != null && author.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("SearchProductRecord{id='%s', title='%s', author='%s', price=%.2f, available=%s}",
                id != null ? id : "null",
                title != null ? title : "null",
                author != null ? author : "null",
                getEffectivePrice(),
                isAvailable());
    }
}