package twitter4jads.models.ads.cards.components;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardComponent {

    @SerializedName("type")
    private ComponentType type;

    @SerializedName("label")
    private Label label;

    @SerializedName("destination")
    private Destination destination;

    @SerializedName("media_key")
    private String mediaKey;

    @SerializedName("media_keys")
    private List<String> mediaKeys;

    @SerializedName("title")
    private String title;


    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getMediaKey() {
        return mediaKey;
    }

    public void setMediaKey(String mediaKey) {
        this.mediaKey = mediaKey;
    }

    public List<String> getMediaKeys() {
        return mediaKeys;
    }

    public void setMediaKeys(List<String> mediaKeys) {
        this.mediaKeys = mediaKeys;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
