package twitter4jads.models.ads;

import com.google.gson.annotations.SerializedName;

/**
 * @author akashMaurya
 * @Date 07/06/16.
 */
public class AudienceEstimate {

    @SerializedName("audience_size")
    private TwitterRange audiencesize;

    public TwitterRange getAudiencesize() {
        return audiencesize;
    }

    public void setAudiencesize(TwitterRange audiencesize) {
        this.audiencesize = audiencesize;
    }
}
