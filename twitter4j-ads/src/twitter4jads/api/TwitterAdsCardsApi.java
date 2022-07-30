package twitter4jads.api;

import com.google.common.base.Optional;
import twitter4jads.BaseAdsListResponseIterable;
import twitter4jads.BaseAdsResponse;
import twitter4jads.internal.models4j.TwitterException;
import twitter4jads.models.ads.cards.*;
import twitter4jads.models.media.TwitterLibraryMedia;

import java.io.IOException;
import java.util.List;

/**
 * User: abhay
 * Date: 4/4/16
 * Time: 8:09 PM
 */
public interface TwitterAdsCardsApi {

    /**
     * @param accountId          The identifier for the leveraged account.
     * @param cardTypes          (optional) Card Types to scope the request to.
     * @param cardIds            (optional) Card Ids to scope the request to.
     * @param cardUris           (optional) Card URIs to scope the request to.
     * @param includeLegacyCards (optional) Include legacy website and app cards in the response.
     * @param q                  (optional) An optional query to scope cards by name
     * @param sortBy             (optional) Sorts by supported attribute in ascending or descending order.
     * @param withDeleted        (optional) Include deleted results in your request. Defaults to false.
     * @param count              (optional) Specifies the number of Image App Download Cards to try and retrieve, up to a maximum of 1000 per distinct request.
     * @param cursor             (optional) Specifies a cursor to get the next page of results.
     * @return details of one or more Image App Download Cards associated with the account
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/image_app_download">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/image_app_download</a>
     */
    BaseAdsListResponseIterable<Card> getCards(String accountId,
                                               List<CardType> cardTypes,
                                               List<String> cardIds,
                                               List<String> cardUris,
                                               boolean includeLegacyCards,
                                               Optional<String> q,
                                               Optional<String> sortBy,
                                               boolean withDeleted,
                                               Optional<Integer> count,
                                               Optional<String> cursor) throws TwitterException;


    /**
     * @param card The card to be created. (required)
     * @return details of the created card if successful
     * @throws TwitterException
     */
    BaseAdsResponse<Card> createCard(Card card) throws TwitterException;

    /**
     * The request will replace each field with the parameters specified within the payload.
     *
     * @param card The card to be updated. (required)
     * @return details of the created card if successful
     * @throws TwitterException
     */
    BaseAdsResponse<Card> updateCard(Card card) throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param cardId    The identifier of the Card to be deleted.
     * @return Details of the deleted card with deleted true, if successful
     * @throws TwitterException
     */
    BaseAdsResponse<Card> deleteCard(String accountId, String cardId) throws TwitterException;

    /**
     * @param accountId   The identifier for the leveraged account.
     * @param cardIds     (optional) Image App Download Card identifiers to scope the request to. If not provided returns all the Image App Download Cards.
     * @param withDeleted (optional) Include deleted results in your request. Defaults to false.
     * @param count       (optional) Specifies the number of Image App Download Cards to try and retrieve, up to a maximum of 1000 per distinct request.
     * @return details of one or more Image App Download Cards associated with the account
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/image_app_download">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/image_app_download</a>
     */
    @Deprecated
    BaseAdsListResponseIterable<TwitterImageAppDownloadCard> getAllImageAppDownloadCards(String accountId, List<String> cardIds, boolean withDeleted,
                                                                                         Optional<Integer> count) throws TwitterException;

    /**
     * @param accountId   The identifier for the leveraged account.
     * @param cardIds     (optional) Video App Download Card identifiers to fetch. If not provided returns all the Video App Download Cards.
     * @param withDeleted (optional) Include deleted results in your request. Defaults to false.
     * @param count       (optional) Specifies the number of Video App Download Cards to try and retrieve, up to a maximum of 1000 per distinct request.
     * @return details of one or more Video App Download Cards associated with the account
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/video_app_download">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/video_app_download</a>
     */
    @Deprecated
    BaseAdsListResponseIterable<TwitterVideoAppDownloadCard> getAllVideoAppDownloadCards(String accountId, List<String> cardIds, boolean withDeleted,
                                                                                         Optional<Integer> count) throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param cardId    The identifier of the card to be deleted.
     * @return Details of the deleted card with deleted true, if successful
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/website/%3Acard_id">https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/website/%3Acard_id</a>
     */
    @Deprecated
    BaseAdsResponse<TwitterWebsiteCard> deleteWebsiteCard(String accountId, String cardId) throws TwitterException;

    BaseAdsListResponseIterable<TwitterImageConversationCard> getAllImageConversationCards(String accountId, List<String> cardIds,
                                                                                           boolean withDeleted, Integer count)
            throws TwitterException;

    BaseAdsListResponseIterable<TwitterVideoConversationCard> getAllVideoConversationCards(String accountId, List<String> cardIds,
                                                                                           boolean withDeleted, Integer count)
            throws TwitterException;

    @Deprecated
    BaseAdsListResponseIterable<TwitterVideoWebsiteCard> getAllVideoWebsiteCards(String accountId, List<String> cardIds,
                                                                                 boolean withDeleted, Integer count)
            throws TwitterException;

    /**
     * @param accountId   The identifier for the leveraged account.
     * @param cardIds     (optional) Website Card identifiers to fetch. If not provided returns all the Website Cards.
     * @param withDeleted (optional) Include deleted results in your request. Defaults to false.
     * @param count       (optional) Specifies the number of website cards to try and retrieve, up to a maximum of 1000 per distinct request.
     * @return retrieves details of one or more Website Cards associated with the account
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/website">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/website</a>
     */
    @Deprecated
    BaseAdsListResponseIterable<TwitterWebsiteCard> getAllWebsiteCards(String accountId, List<String> cardIds, boolean withDeleted, Optional<Integer> count)
            throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param cardId    Website Card identifier to fetch.
     * @return retrieved card details
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/website/%3Acard_id">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/website/%3Acard_id</a>
     */

    @Deprecated
    BaseAdsResponse<TwitterWebsiteCard> getWebsiteCard(String accountId, String cardId) throws TwitterException;

    /**
     * @param accountId   The identifier for the leveraged account.
     * @param cardIds     (optional) App Download Card identifiers to fetch. If not provided returns all the App Download Cards.
     * @param withDeleted (optional) Include deleted results in your request. Defaults to false.
     * @param count       (optional) Specifies the number of App Download Cards to try and retrieve, up to a maximum of 1000 per distinct request.
     * @return retrieves details of ome or all App Download Cards associated with the account
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/app_download">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/app_download</a>
     */
    @Deprecated
    BaseAdsListResponseIterable<TwitterMobileAppCard> getAllAppDownloadCards(String accountId, List<String> cardIds, boolean withDeleted,
                                                                             Optional<Integer> count) throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param cardId    App Download Card identifier to fetch.
     * @return retrieved card details
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/app_download/%3Acard_id">https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/cards/app_download/%3Acard_id</a>
     */
    @Deprecated
    BaseAdsResponse<TwitterMobileAppCard> getAppDownloadCard(String accountId, String cardId) throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param cardId    The identifier of the App Download Card to be deleted.
     * @return Details of the deleted card with deleted true, if successful
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/app_download/%3Acard_id">https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/app_download/%3Acard_id</a>
     */
    @Deprecated
    BaseAdsResponse<TwitterMobileAppCard> deleteAppDownloadCard(String accountId, String cardId) throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param cardId    The identifier of the Video App Download Card to be deleted.
     * @return Details of the deleted card with deleted true, if successful
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/video_app_download/%3Aid">https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/video_app_download/%3Aid</a>
     */
    @Deprecated
    BaseAdsResponse<TwitterVideoAppDownloadCard> deleteVideoAppDownloadCard(String accountId, String cardId) throws TwitterException;

    /**
     * @param accountId The identifier for the leveraged account.
     * @param cardId    The identifier of the Image App Download Card to be deleted.
     * @return Details of the deleted card with deleted true, if successful
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/image_app_download/%3Acard_id">https://dev.twitter.com/ads/reference/delete/accounts/%3Aaccount_id/cards/image_app_download/%3Acard_id</a>
     */
    @Deprecated
    BaseAdsResponse<TwitterImageAppDownloadCard> deleteImageAppDownloadCard(String accountId, String cardId) throws TwitterException;

    /**
     * @param imageTonLocation The TON server location of image file to be used.
     * @return response of posting the video card image file
     * @throws TwitterException
     */
    @Deprecated
    String postVideoCardImage(String imageTonLocation) throws TwitterException;

    // ---  Stats  ---

    /**
     * @param accountId   The identifier for the leveraged account.
     * @param cardId      Lead Generation Card identifier to fetch stats for.
     * @param startTime   The time to collect stats from.
     * @param endTime     The time to collect stats until.
     * @param granularity (optional) The granularity such as DAY or HOUR as String.
     * @param metric      (optional) Specifies the number of App Download Cards to try and retrieve, up to a maximum of 1000 per distinct request.
     * @param withDeleted (optional) Specifies the number of App Download Cards to try and retrieve, up to a maximum of 1000 per distinct request.
     * @return retrieves details of ome or all App Download Cards associated with the account
     * @throws TwitterException
     * @see <a href="https://dev.twitter.com/ads/reference/1/get/stats/accounts/%3Aaccount_id">https://dev.twitter.com/ads/reference/1/get/stats/accounts/%3Aaccount_id</a>
     */
    @Deprecated
    BaseAdsResponse<TwitterLeadGenerationStat> getTwitterLeadGenerationStat(String accountId, String cardId, String startTime, Optional<String> endTime,
                                                                            Optional<String> granularity, Optional<String> metric, Optional<Boolean> withDeleted)
            throws TwitterException;

    /**
     * @param accountId    The identifier for the leveraged account. (required)
     * @param name         The name identifier for card. Maximum length: 80 characters. (required)
     * @param cardId       The identifier of the card to be updated. (required)
     * @param websiteTitle The title of the website card. Maximum length: 70 characters. (required)
     * @param websiteUrl   The URL of the website to redirect a user to. Maximum length: 200 characters. (required)
     * @return details of the updated card if successful
     */
    @Deprecated
    BaseAdsResponse<TwitterWebsiteCard> updateWebsiteCard(String accountId, String name, String cardId, String websiteTitle, String websiteUrl,
                                                          String imageMedaKey) throws TwitterException;

    /**
     * @param accountId    The identifier for the leveraged account. (required)
     * @param name         The name identifier for card. Maximum length: 80 characters. (required)
     * @param websiteTitle The title of the website card. Maximum length: 70 characters. (required)
     * @param websiteUrl   The URL of the website to redirect a user to. Maximum length: 200 characters. (required)
     * @return details of the created card if successful
     * @throws TwitterException
     */
    @Deprecated
    BaseAdsResponse<TwitterWebsiteCard> createWebsiteCard(String accountId, String name, String websiteTitle,
                                                          String websiteUrl, String imageMedaKey)
            throws TwitterException;

    /**
     * @param accountId            The identifier for the leveraged account. (required)
     * @param name                 The name identifier for card. Maximum length: 80 characters. (required)
     * @param countryCode          2 letter ISO code for the country where the App is sold. (required)
     * @param iphoneAppId          This is usually numeric and available in your app store URL. For example,
     *                             333903271 is the id for twitter. You can retrieve the id from Apple App Store URL - https://itunes.apple.com/us/app/twitter/id<IPHONE_APP_ID>
     * @param ipadAppId            This is usually numeric and available in your app store URL. For example,
     *                             333903271 is the id for twitter. You can retrieve the id from Apple App Store URL - https://itunes.apple.com/us/app/twitter/id<IPAD_APP_ID>
     * @param googlePlayAppId      This ID is googleplay’s application package name. For example, twitter’s google play app id is com.twitter.android.
     * @param iphoneDeepLink       This is your app's deep link.
     * @param ipadDeepLink         This is your app's deep link.
     * @param googlePlayDeepLink   This is your app's deep link.
     * @param customAppDescription This is a custom description of the app. If supplied, it will be used instead of the description from the app store.
     * @return details of the created card if successful
     */
    @Deprecated
    BaseAdsResponse<TwitterMobileAppCard> createAppDownloadCard(String accountId, String name, String countryCode, String iphoneAppId,
                                                                String ipadAppId, String googlePlayAppId, String iphoneDeepLink, String ipadDeepLink,
                                                                String googlePlayDeepLink, String imageMedaKey,
                                                                String customAppDescription, String callToAction) throws TwitterException;

    /**
     * @param accountId            The identifier for the leveraged account. (required)
     * @param name                 The name identifier for card. Maximum length: 80 characters. (required)
     * @param cardId               The identifier of the card to be updated
     * @param countryCode          2 letter ISO code for the country where the App is sold. (required)
     * @param iphoneAppId          This is usually numeric and available in your app store URL. For example,
     *                             333903271 is the id for twitter. You can retrieve the id from Apple App Store URL - https://itunes.apple.com/us/app/twitter/id<IPHONE_APP_ID>
     * @param ipadAppId            This is usually numeric and available in your app store URL. For example,
     *                             333903271 is the id for twitter. You can retrieve the id from Apple App Store URL - https://itunes.apple.com/us/app/twitter/id<IPAD_APP_ID>
     * @param googlePlayAppId      This ID is googleplay’s application package name. For example, twitter’s google play app id is com.twitter.android.
     * @param iphoneDeepLink       This is your app's deep link.
     * @param ipadDeepLink         This is your app's deep link.
     * @param googlePlayDeepLink   This is your app's deep link.
     * @param customAppDescription This is a custom description of the app. If supplied, it will be used instead of the description from the app store.
     * @return details of the updated card if successful
     */
    @Deprecated
    BaseAdsResponse<TwitterMobileAppCard> updateAppDownloadCard(String accountId, String name, String cardId, String countryCode,
                                                                String iphoneAppId, String ipadAppId, String googlePlayAppId, String iphoneDeepLink,
                                                                String ipadDeepLink, String googlePlayDeepLink, String imageMedaKey,
                                                                String customAppDescription, String callToAction)
            throws TwitterException;


    @Deprecated
    BaseAdsResponse<TwitterImageAppDownloadCard> createImageAppDownloadCard(String accountId, String name, String countryCode, String iphoneAppId,
                                                                            String ipadAppId, String googlePlayAppId, String iphoneDeepLink,
                                                                            String ipadDeepLink, String googlePlayDeepLink, String imageMediaKey,
                                                                            String callToAction) throws TwitterException;

    @Deprecated
    BaseAdsResponse<TwitterImageAppDownloadCard> updateImageAppDownloadCard(String accountId, String name, String cardId, String countryCode,
                                                                            String iphoneAppId, String ipadAppId, String googlePlayAppId,
                                                                            String iphoneDeepLink, String ipadDeepLink, String googlePlayDeepLink,
                                                                            String imageMedaKey, String callToAction)
            throws TwitterException;

    @Deprecated
    BaseAdsResponse<TwitterVideoAppDownloadCard> createVideoAppDownloadCard(String accountId, String name, String countryCode, String iphoneAppId,
                                                                            String ipadAppId, String googlePlayAppId, String iphoneDeepLink,
                                                                            String ipadDeepLink, String googlePlayDeepLink, String imageMedaKey,
                                                                            String callToAction, TwitterLibraryMedia twitterVideo)
            throws TwitterException, IOException, InterruptedException;

    @Deprecated
    BaseAdsResponse<TwitterVideoAppDownloadCard> updateVideoAppDownloadCard(String accountId, String name, String cardId, String countryCode,
                                                                            String iphoneAppId, String ipadAppId, String googlePlayAppId,
                                                                            String iphoneDeepLink, String ipadDeepLink, String googlePlayDeepLink,
                                                                            String imageMedaKey, String callToActionValue, TwitterLibraryMedia video)
            throws TwitterException, IOException, InterruptedException;


    BaseAdsResponse<TwitterImageConversationCard> createImageConversationCard(String accountId, String name, String title, String firstHashtag,
                                                                              String firstTweet, String secondHashtag, String secondTweet,
                                                                              String thirdHashtag, String thirdTweet, String fourthHashtag,
                                                                              String fourthTweet, String thanksText, String thanksUrl,
                                                                              String imageUrl, String imageMedaKey) throws TwitterException;

    BaseAdsResponse<TwitterImageConversationCard> updateImageConversationCard(String accountId, String cardId, String name, String title,
                                                                              String firstHashtag, String firstTweet, String secondHashtag,
                                                                              String secondTweet, String thirdHashtag, String thirdTweet,
                                                                              String fourthHashtag, String fourthTweet, String thanksText,
                                                                              String thanksUrl, String imageUrl, String imageMedaKey)
            throws TwitterException;


    BaseAdsResponse<TwitterImageConversationCard> deleteImageConversationCard(String accountId, String cardId) throws TwitterException;

    BaseAdsResponse<TwitterVideoConversationCard> createVideoConversationCard(String accountId, String name, String title, String firstHashtag,
                                                                              String firstTweet, String secondHashtag, String secondTweet,
                                                                              String thirdHashtag, String thirdTweet, String fourthHashtag,
                                                                              String fourthTweet, String thanksText, String thanksUrl,
                                                                              String imageMedaKey, TwitterLibraryMedia twitterVideo)
            throws TwitterException;


    BaseAdsResponse<TwitterVideoConversationCard> updateVideoConversationCard(String accountId, String cardId, String name, String title,
                                                                              String firstHashtag, String firstTweet, String secondHashtag,
                                                                              String secondTweet, String thirdHashtag, String thirdTweet,
                                                                              String fourthHashtag, String fourthTweet, String thanksText,
                                                                              String thanksUrl, String imageMedaKey, TwitterLibraryMedia twitterVideo)
            throws TwitterException;


    BaseAdsResponse<TwitterVideoConversationCard> deleteVideoConversationCard(String accountId, String cardId) throws TwitterException;


    @Deprecated
    BaseAdsResponse<TwitterVideoWebsiteCard> createVideoWebsiteCard(String accountId, String name, String title, String videoKey, String websiteUrl)
            throws TwitterException;


    @Deprecated
    BaseAdsResponse<TwitterVideoWebsiteCard> updateVideoWebsiteCard(String accountId, String cardId, String name, String title, String videoKey,
                                                                    String websiteUrl) throws TwitterException;

    @Deprecated
    BaseAdsResponse<TwitterVideoWebsiteCard> deleteVideoWebsiteCard(String accountId, String cardId) throws TwitterException;


}
