package twitter4jads.models.ads.cards.components;

import com.google.gson.annotations.SerializedName;

public class Label {

    @SerializedName("type")
    private LabelType type;

    @SerializedName("value")
    private LabelValue value;

    public LabelType getType() {
        return type;
    }

    public void setType(LabelType type) {
        this.type = type;
    }

    public LabelValue getValue() {
        return value;
    }

    public void setValue(LabelValue value) {
        this.value = value;
    }
}
