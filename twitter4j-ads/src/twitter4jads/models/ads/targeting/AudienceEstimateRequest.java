package twitter4jads.models.ads.targeting;

import com.google.gson.annotations.SerializedName;
import twitter4jads.models.ads.TargetingCriteria;

import java.util.List;

public class AudienceEstimateRequest {

    @SerializedName("targeting_criteria")
    private List<TargetingCriteria> targetingCriteria;

    public List<TargetingCriteria> getTargetingCriteria() {
        return targetingCriteria;
    }

    public void setTargetingCriteria(List<TargetingCriteria> targetingCriteria) {
        this.targetingCriteria = targetingCriteria;
    }
}
