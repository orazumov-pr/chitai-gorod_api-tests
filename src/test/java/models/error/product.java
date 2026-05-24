package models.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductErrorResponseRecord(
        @JsonProperty("error") String error,
        @JsonProperty("statusCode") Integer statusCode,
        @JsonProperty("message") String message
) {
    public boolean isNotFoundError() {
        return error != null && (error.contains("не найден") || error.contains("not found"));
    }

    public boolean isBadRequestError() {
        return statusCode != null && statusCode == 400;
    }
}
