package twitter4jads.models.ads.audience;

import com.google.gson.annotations.SerializedName;
import twitter4jads.models.ads.TwitterEntity;

/**
 * Copyright (c) 2016-2017 Sprinklr, Inc. All rights reserved.
 * Created with Intellij IDEA.
 * User: ratneshjd
 * Date: 03/04/17
 * Time: 11:51 AM
 */
public class CustomAudienceMatchingRules extends TwitterEntity {

    public static final String CUSTOM_AUDIENCE_ID = "custom_audience_id";
    public static final String WEBSITE_TAG_ID = "website_tag_id";
    public static final String RULE_TYPE = "rule_type";
    private static final String RULE_VALUE = "rule_value";
    private static final String DELETED = "deleted";


    @SerializedName(CUSTOM_AUDIENCE_ID)
    private String customAudienceId;

    @SerializedName(WEBSITE_TAG_ID)
    private String websiteTagId;

    @SerializedName(RULE_TYPE)
    private CustomAudienceMatchingRuleType ruleType;

    @SerializedName(RULE_VALUE)
    private String ruleValue;

    @SerializedName(DELETED)
    private Boolean deleted;

    public String getCustomAudienceId() {
        return customAudienceId;
    }

    public void setCustomAudienceId(String customAudienceId) {
        this.customAudienceId = customAudienceId;
    }

    public String getWebsiteTagId() {
        return websiteTagId;
    }

    public void setWebsiteTagId(String websiteTagId) {
        this.websiteTagId = websiteTagId;
    }

    public CustomAudienceMatchingRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(CustomAudienceMatchingRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public enum CustomAudienceMatchingRuleType {
        CONTAINS,
        EXACT,
        MATCH_ALL
    }

}
