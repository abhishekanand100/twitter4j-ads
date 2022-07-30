package twitter4jads.models.video;

import com.google.gson.annotations.SerializedName;

/**
 * User: abhishekanand
 * Date: 18/04/16 8:43 PM.
 */
public class UploadMediaObjectResponse {

    @SerializedName("media_id")
    private String mediaId;

    @SerializedName("media_key")
    private String mediaKey;

    @SerializedName("size")
    private Long size;

    @SerializedName("expires_after_secs")
    private Long expiresAfterSec;

    @SerializedName("video")
    private VideoContentType videoContentType;

    @SerializedName("processing_info")
    private UploadMediaProcessingInfo uploadMediaProcessingInfo;

    public String getMediaKey() {
        return mediaKey;
    }

    public void setMediaKey(String mediaKey) {
        this.mediaKey = mediaKey;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getExpiresAfterSec() {
        return expiresAfterSec;
    }

    public void setExpiresAfterSec(Long expiresAfterSec) {
        this.expiresAfterSec = expiresAfterSec;
    }

    public VideoContentType getVideoContentType() {
        return videoContentType;
    }

    public void setVideoContentType(VideoContentType videoContentType) {
        this.videoContentType = videoContentType;
    }

    public UploadMediaProcessingInfo getUploadMediaProcessingInfo() {
        return uploadMediaProcessingInfo;
    }

    public void setUploadMediaProcessingInfo(UploadMediaProcessingInfo uploadMediaProcessingInfo) {
        this.uploadMediaProcessingInfo = uploadMediaProcessingInfo;
    }
}
