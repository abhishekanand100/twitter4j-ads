package twitter4jads.api;

import com.google.common.base.Optional;
import twitter4jads.BaseAdsListBatchPostResponse;
import twitter4jads.BaseAdsListResponse;
import twitter4jads.BaseAdsListResponseIterable;
import twitter4jads.BaseAdsResponse;
import twitter4jads.internal.models4j.TwitterException;
import twitter4jads.models.ads.CustomAudience;
import twitter4jads.models.ads.audience.CustomAudienceMatchingRules;
import twitter4jads.models.ads.audience.CustomAudienceOperation;
import twitter4jads.models.ads.audience.CustomAudiencePermission;

import java.util.List;

/**
 * User: abhay
 * Date: 4/5/16
 * Time: 10:54 AM
 */
public interface TwitterAdsAudienceApi {

    /**
     * @param accountId   The identifier for the leveraged account.
     * @param withDeleted (optional) Include deleted results in your request. Defaults to false.
     * @param count       (optional) Limit the number returned per page of requests to the specified amount.
     * @param cursor      (optional) Specifies a cursor to get the next page of CustomAudience objects (function automatically handles paging upon iteration when you do not specify cursor value).
     * @param q           (optional) An optional query to scope resource by name.
     * @return the collection of CustomAudience objects belonging to the authenticated user.
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/custom_audiences">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/custom_audiences</a>
     */
    BaseAdsListResponseIterable<CustomAudience> getAllCustomAudiences(String accountId, Optional<Integer> count,
                                                                      Optional<Boolean> withDeleted, Optional<String> cursor,
                                                                      Optional<String> q)
            throws TwitterException;

    /**
     * @param accountId          The identifier for the leveraged account.
     * @param customAudienceId The identifier for a specific tailored audience.
     * @return detailed information on a specific tailored audience.
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/custom_audiences/%3Aid">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/custom_audiences/%3Aid</a>
     */
    BaseAdsResponse<CustomAudience> getCustomAudienceForId(String accountId, String customAudienceId) throws TwitterException;

    /**
     * @param accountId          The identifier for the leveraged account.
     * @param customAudienceId The identifier for a specific tailored audience.
     * @return response of deleting a tailored audience.
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/custom_audiences/%3Aid">https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/custom_audiences/%3Aid</a>
     */
    BaseAdsResponse<CustomAudience> deleteCustomAudience(String accountId, String customAudienceId) throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param name      The name of the tailored audience to create.
     * @return response of creating a tailored audience.
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/post/accounts/%3Aaccount_id/custom_audiences">https://dev.twitter.com/ads/reference/post/accounts/%3Aaccount_id/custom_audiences</a>
     */
    BaseAdsResponse<CustomAudience> createCustomAudience(String accountId, String name) throws TwitterException;

    /**
     * @param customAudienceMatchingRules
     * @param accountId                     The identifier for the leveraged account.
     * @return Resultant matching rules for tailored audiences
     * @throws TwitterException
     */
    BaseAdsResponse<CustomAudienceMatchingRules> addMatchingRulesToAudience(CustomAudienceMatchingRules customAudienceMatchingRules,
                                                                              String accountId)
            throws TwitterException;

    /**
     * Update tailored audience using specified operation.
     * Returns operations as actually sent to twitter.
     * The supplied operations will not be mixed but might be broken down into smaller operations for batch processing.
     * <p>
     * The operations returned are not guaranteed to be in the same order as when supplied.
     * Only the user detail correspondence with the effective, expire and operation fields is maintained.
     * <p>
     * Batching of operations is taken care automatically
     * Returns on first batch that fails.
     *
     * @param accountId          The identifier for the leveraged account.
     * @param customAudienceId The identifier for a specific tailored audience.
     * @param operations         List of tailored audience operations to be performed
     * @return On successful update returns list of operations as actually sent with batching. On failure, last operation in list will be the one that failed.
     * @throws TwitterException
     */
    List<CustomAudienceOperation> updateCustomAudienceById(String accountId, String customAudienceId,
                                                               List<CustomAudienceOperation> operations)
            throws TwitterException;

    /**
     * @param accountId
     * @param requestBody
     * @return
     * @throws TwitterException
     */
    BaseAdsListBatchPostResponse<CustomAudience> createFlexibleCustomAudience(String accountId, String requestBody) throws TwitterException;

    /**
     * @param accountId
     * @param customAudienceId
     * @return
     * @throws TwitterException
     */
    BaseAdsListResponse<CustomAudiencePermission> getCustomAudiencePermission(String accountId, String customAudienceId) throws
            TwitterException;

    /**
     * @param accountId
     * @param customAudienceId
     * @param grantedAccountId
     * @return
     * @throws TwitterException
     */
    BaseAdsResponse<CustomAudiencePermission> shareCustomAudience(String accountId, String customAudienceId, String
            grantedAccountId) throws TwitterException;
}
