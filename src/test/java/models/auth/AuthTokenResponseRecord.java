package models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthTokenResponseRecord(
        @JsonProperty("accessToken") String accessToken,
        @JsonProperty("tokenType") String tokenType,
        @JsonProperty("expiresIn") Integer expiresIn
) {
    public boolean isValid() {
        return accessToken != null && !accessToken.isEmpty();
    }

    public String getBearerToken() {
        return "Bearer " + accessToken;
    }
}
