package twitter4jads.models.ads;

import com.google.gson.annotations.SerializedName;
import twitter4jads.models.ads.audience.CustomAudiencePermissionLevel;
import twitter4jads.models.ads.audience.CustomAudienceType;

import java.util.Date;
import java.util.List;

/**
 * User: abhishekanand
 * Date: 10/10/14
 * Time: 10:51 PM
 */
public class CustomAudience extends TwitterEntity {

    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String NAME = "name";
    public static final String TARGETING_TYPE = "targeting_type";
    public static final String AUDIENCE_TYPE = "audience_type";
    public static final String AUDIENCE_SIZE = "audience_size";
    public static final String TARGETABLE = "targetable";
    public static final String TARGETABLE_TYPES = "targetable_types";
    public static final String REASONS_NOT_TARGETABLE = "reasons_not_targetable";
    public static final String DELETED = "deleted";
    public static final String PARTNER_SOURCE = "partner_source";
    public static final String PERMISSION_LEVEL = "permission_level";
    public static final String OWNER_ACCOUNT_ID = "owner_account_id";

    @SerializedName(CREATED_AT)
    private Date createdAt;

    @SerializedName(UPDATED_AT)
    private Date updatedAt;

    @SerializedName(NAME)
    private String name;

    @SerializedName(TARGETING_TYPE)
    private TargetingType targetingType;

    @SerializedName(AUDIENCE_TYPE)
    private CustomAudienceType customAudienceType;

    @SerializedName(AUDIENCE_SIZE)
    private String audienceSize;

    @SerializedName(TARGETABLE)
    private Boolean isTargetable;

    @SerializedName(TARGETABLE_TYPES)
    private List<CustomAudienceType> targetableTypes;

    @SerializedName(REASONS_NOT_TARGETABLE)
    private List<String> reasonsNotTargetable;

    @SerializedName(DELETED)
    private Boolean deleted;

    @SerializedName(PARTNER_SOURCE)
    private String partnerSource;

    @SerializedName(PERMISSION_LEVEL)
    private CustomAudiencePermissionLevel permissionLevel;

    @SerializedName(OWNER_ACCOUNT_ID)
    private String ownerAccountId;

    public CustomAudiencePermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(CustomAudiencePermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(String ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public String getPartnerSource() {
        return partnerSource;
    }

    public void setPartnerSource(String partnerSource) {
        this.partnerSource = partnerSource;
    }

    public Boolean getTargetable() {
        return isTargetable;
    }

    public void setTargetable(Boolean targetable) {
        isTargetable = targetable;
    }

    public List<CustomAudienceType> getTargetableTypes() {
        return targetableTypes;
    }

    public void setTargetableTypes(List<CustomAudienceType> targetableTypes) {
        this.targetableTypes = targetableTypes;
    }

    public List<String> getReasonsNotTargetable() {
        return reasonsNotTargetable;
    }

    public void setReasonsNotTargetable(List<String> reasonsNotTargetable) {
        this.reasonsNotTargetable = reasonsNotTargetable;
    }

    public CustomAudienceType getCustomAudienceType() {
        return customAudienceType;
    }

    public void setCustomAudienceType(CustomAudienceType customAudienceType) {
        this.customAudienceType = customAudienceType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public TargetingType getTargetingType() {
        return targetingType;
    }

    public void setTargetingType(TargetingType targetingType) {
        this.targetingType = targetingType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getAudienceSize() {
        return audienceSize;
    }

    public void setAudienceSize(String audienceSize) {
        this.audienceSize = audienceSize;
    }
}
