package models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GuestAuthRequestRecord(
        @JsonProperty("device") String device,
        @JsonProperty("guest") Boolean isGuest
) {
    public static GuestAuthRequestRecord desktopGuest() {
        return new GuestAuthRequestRecord("desktop", true);
    }

    public static GuestAuthRequestRecord mobileGuest() {
        return new GuestAuthRequestRecord("mobile", true);
    }
}
