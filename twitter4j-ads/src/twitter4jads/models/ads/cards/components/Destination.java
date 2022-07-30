package twitter4jads.models.ads.cards.components;

import com.google.gson.annotations.SerializedName;

public class Destination {

    @SerializedName("type")
    private DestinationType type;

    @SerializedName("url")
    private String url;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("googleplay_app_id")
    private String googleplayAppId;

    @SerializedName("ios_app_store_identifier")
    private String iosAppStoreIdentifier;

    @SerializedName("googleplay_deep_link")
    private String googleplayDeepLink;

    @SerializedName("ios_deep_link")
    private String iosDeepLink;

    public DestinationType getType() {
        return type;
    }

    public void setType(DestinationType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getGoogleplayAppId() {
        return googleplayAppId;
    }

    public void setGoogleplayAppId(String googleplayAppId) {
        this.googleplayAppId = googleplayAppId;
    }

    public String getIosAppStoreIdentifier() {
        return iosAppStoreIdentifier;
    }

    public void setIosAppStoreIdentifier(String iosAppStoreIdentifier) {
        this.iosAppStoreIdentifier = iosAppStoreIdentifier;
    }

    public String getGoogleplayDeepLink() {
        return googleplayDeepLink;
    }

    public void setGoogleplayDeepLink(String googleplayDeepLink) {
        this.googleplayDeepLink = googleplayDeepLink;
    }

    public String getIosDeepLink() {
        return iosDeepLink;
    }

    public void setIosDeepLink(String iosDeepLink) {
        this.iosDeepLink = iosDeepLink;
    }
}
