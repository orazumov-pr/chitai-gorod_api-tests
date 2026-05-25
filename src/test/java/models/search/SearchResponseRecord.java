package models.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponseRecord(
        @JsonProperty("totalCount") Integer totalCount,
        @JsonProperty("page") Integer page,
        @JsonProperty("totalPages") Integer totalPages,
        @JsonProperty("data") List<SearchProductRecord> data
) {

    // ============ ГЕТТЕРЫ ДЛЯ СОВМЕСТИМОСТИ С СУЩЕСТВУЮЩИМИ ТЕСТАМИ ============

    /**
     * Геттер для получения общего количества результатов
     * @return количество найденных товаров
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * Геттер для получения текущей страницы
     * @return номер страницы
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Геттер для получения общего количества страниц
     * @return количество страниц
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * Геттер для получения списка товаров
     * @return список найденных товаров
     */
    public List<SearchProductRecord> getData() {
        return data;
    }

    // ============ МЕТОДЫ-ПОМОЩНИКИ ДЛЯ ПРОВЕРОК ============

    /**
     * Проверка, есть ли результаты поиска
     * @return true если есть хотя бы один результат
     */
    public boolean hasResults() {
        return totalCount != null && totalCount > 0 && data != null && !data.isEmpty();
    }

    /**
     * Получение первого результата поиска
     * @return первый товар или null
     */
    public SearchProductRecord getFirstResult() {
        return data != null && !data.isEmpty() ? data.get(0) : null;
    }

    /**
     * Получение последнего результата поиска
     * @return последний товар или null
     */
    public SearchProductRecord getLastResult() {
        return data != null && !data.isEmpty() ? data.get(data.size() - 1) : null;
    }

    /**
     * Получение количества результатов на текущей странице
     * @return количество результатов
     */
    public int getCurrentPageSize() {
        return data != null ? data.size() : 0;
    }

    /**
     * Поиск товара по ID
     * @param productId ID товара
     * @return товар или null
     */
    public SearchProductRecord findProductById(String productId) {
        if (data == null) return null;
        return data.stream()
                .filter(product -> product.id().equals(productId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск товара по названию (частичное совпадение)
     * @param titleKeyword ключевое слово в названии
     * @return товар или null
     */
    public SearchProductRecord findProductByTitle(String titleKeyword) {
        if (data == null) return null;
        return data.stream()
                .filter(product -> product.title().toLowerCase().contains(titleKeyword.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Проверка, что все результаты содержат ключевое слово
     * @param keyword ключевое слово для поиска
     * @return true если все результаты релевантны
     */
    public boolean areAllResultsRelevant(String keyword) {
        if (data == null) return true;
        return data.stream()
                .allMatch(product ->
                        product.title().toLowerCase().contains(keyword.toLowerCase()) ||
                                (product.author() != null && product.author().toLowerCase().contains(keyword.toLowerCase()))
                );
    }

    /**
     * Получение списка ID всех найденных товаров
     * @return список ID
     */
    public List<String> getAllProductIds() {
        if (data == null) return List.of();
        return data.stream()
                .map(SearchProductRecord::id)
                .toList();
    }

    /**
     * Получение общего количества (безопасно от null)
     * @return количество (0 если totalCount == null)
     */
    public int getActualTotalCount() {
        return totalCount != null ? totalCount : 0;
    }

    /**
     * Получение общего количества страниц (безопасно от null)
     * @return количество страниц (0 если totalPages == null)
     */
    public int getActualTotalPages() {
        return totalPages != null ? totalPages : 0;
    }

    /**
     * Проверка, что количество результатов на странице не превышает лимит
     * @param limit максимальный лимит
     * @return true если не превышает
     */
    public boolean isPageSizeValid(int limit) {
        return getCurrentPageSize() <= limit;
    }

    @Override
    public String toString() {
        return String.format("SearchResponseRecord{totalCount=%d, page=%d, totalPages=%d, resultsCount=%d}",
                getActualTotalCount(),
                page != null ? page : 0,
                getActualTotalPages(),
                getCurrentPageSize());
    }
}