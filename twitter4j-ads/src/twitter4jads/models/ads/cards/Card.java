package twitter4jads.models.ads.cards;

import com.google.gson.annotations.SerializedName;
import twitter4jads.models.ads.cards.components.CardComponent;

import java.util.List;

public class Card {

    @SerializedName("account_id")
    private String accountId;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("card_type")
    private CardType cardType;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("card_uri")
    private String cardUri;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("deleted")
    private boolean deleted;

    @SerializedName("components")
    private List<CardComponent> components;

    @SerializedName("slides")
    private List<List<CardComponent>> slides;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCardUri() {
        return cardUri;
    }

    public void setCardUri(String cardUri) {
        this.cardUri = cardUri;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<CardComponent> getComponents() {
        return components;
    }

    public void setComponents(List<CardComponent> components) {
        this.components = components;
    }

    public List<List<CardComponent>> getSlides() {
        return slides;
    }

    public void setSlides(List<List<CardComponent>> slides) {
        this.slides = slides;
    }
}
