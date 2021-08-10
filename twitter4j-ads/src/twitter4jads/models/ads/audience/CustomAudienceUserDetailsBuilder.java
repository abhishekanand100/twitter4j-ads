package twitter4jads.models.ads.audience;

import java.util.HashSet;
import java.util.Set;

/**
 * Builder for CustomAudienceUserDetails
 * User: mayankbhargava
 *
 * @date 26/11/18
 * @time 3:04 AM
 * @see CustomAudienceUserDetails
 */
public class CustomAudienceUserDetailsBuilder {

    private Set<String> emails = new HashSet<>();
    private Set<String> phoneNumbers = new HashSet<>();
    private Set<String> deviceIds = new HashSet<>();
    private Set<String> twitterHandles = new HashSet<>();
    private Set<String> twitterIds = new HashSet<>();

    public CustomAudienceUserDetailsBuilder() {
    }

    public CustomAudienceUserDetailsBuilder addEmail(String hashedEmail) {
        emails.add(hashedEmail);
        return this;
    }

    public CustomAudienceUserDetailsBuilder addPhoneNumber(String hashedPhoneNumber) {
        phoneNumbers.add(hashedPhoneNumber);
        return this;
    }

    public CustomAudienceUserDetailsBuilder addDeviceId(String hashedDeviceId) {
        deviceIds.add(hashedDeviceId);
        return this;
    }

    public CustomAudienceUserDetailsBuilder addTwitterHandle(String hashedTwitterHandle) {
        twitterHandles.add(hashedTwitterHandle);
        return this;
    }

    public CustomAudienceUserDetailsBuilder addTwitterId(String hashedTwitterId) {
        twitterIds.add(hashedTwitterId);
        return this;
    }

    public CustomAudienceUserDetails build() {
        CustomAudienceUserDetails customAudienceUserDetails = new CustomAudienceUserDetails();
        if (!emails.isEmpty()) {
            customAudienceUserDetails.setEmails(emails);
        }
        if (!phoneNumbers.isEmpty()) {
            customAudienceUserDetails.setPhoneNumbers(phoneNumbers);
        }
        if (!deviceIds.isEmpty()) {
            customAudienceUserDetails.setDeviceIds(deviceIds);
        }
        if (!twitterHandles.isEmpty()) {
            customAudienceUserDetails.setTwitterHandles(twitterHandles);
        }
        if (!twitterIds.isEmpty()) {
            customAudienceUserDetails.setTwitterIds(twitterIds);
        }
        return customAudienceUserDetails;
    }
}