package models.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
record SearchProductRecord(
        @JsonProperty("id") String id,
        @JsonProperty("title") String title,
        @JsonProperty("author") String author,
        @JsonProperty("price") Double price,
        @JsonProperty("available") Boolean available
) {}
