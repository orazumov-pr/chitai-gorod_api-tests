package models.error;

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
    public boolean hasResults() {
        return totalCount != null && totalCount > 0 && data != null && !data.isEmpty();
    }

    public SearchProductRecord getFirstResult() {
        return data != null && !data.isEmpty() ? data.get(0) : null;
    }

    public SearchProductRecord findProductByTitle(String titleKeyword) {
        if (data == null) return null;
        return data.stream()
                .filter(product -> product.title().toLowerCase().contains(titleKeyword.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
