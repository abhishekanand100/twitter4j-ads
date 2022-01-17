package twitter4jads.impl;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import twitter4jads.BaseAdsListResponse;
import twitter4jads.BaseAdsListResponseIterable;
import twitter4jads.BaseAdsResponse;
import twitter4jads.TwitterAdsClient;
import twitter4jads.TwitterAdsConstants;
import twitter4jads.api.TwitterAdsTargetingApi;
import twitter4jads.internal.http.HttpParameter;
import twitter4jads.internal.http.HttpResponse;
import twitter4jads.internal.models4j.TwitterException;
import twitter4jads.models.LocationType;
import twitter4jads.models.ads.AppStoreSearchType;
import twitter4jads.models.ads.AudienceEstimate;
import twitter4jads.models.ads.Conversations;
import twitter4jads.models.ads.HttpVerb;
import twitter4jads.models.ads.IabCategory;
import twitter4jads.models.ads.OperatorType;
import twitter4jads.models.ads.SuggestionType;
import twitter4jads.models.ads.TargetingCriteria;
import twitter4jads.models.ads.TargetingLocation;
import twitter4jads.models.ads.TargetingSuggestion;
import twitter4jads.models.ads.TargetingType;
import twitter4jads.models.ads.TwitterAppStore;
import twitter4jads.models.ads.TwitterApplicationDetails;
import twitter4jads.models.ads.TwitterBehavior;
import twitter4jads.models.ads.TwitterBehaviorTaxonomy;
import twitter4jads.models.ads.tags.TwitterApplicationList;
import twitter4jads.models.ads.targeting.AudienceEstimateRequest;
import twitter4jads.models.ads.targeting.TargetingParamRequest;
import twitter4jads.models.ads.targeting.TargetingParamResponse;
import twitter4jads.util.TwitterAdUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static twitter4jads.TwitterAdsConstants.PARAM_AGE_RANGE;
import static twitter4jads.TwitterAdsConstants.PARAM_APP_STORE_CATEGORY;
import static twitter4jads.TwitterAdsConstants.PARAM_APP_STORE_IDENTIFIERS;
import static twitter4jads.TwitterAdsConstants.PARAM_BEHAVIOR;
import static twitter4jads.TwitterAdsConstants.PARAM_BEHAVIOR_EXPANDED;
import static twitter4jads.TwitterAdsConstants.PARAM_BROAD_KEYWORDS;
import static twitter4jads.TwitterAdsConstants.PARAM_CAMPAIGN_ENGAGEMENT;
import static twitter4jads.TwitterAdsConstants.PARAM_CONVERSATIONS;
import static twitter4jads.TwitterAdsConstants.PARAM_COUNT;
import static twitter4jads.TwitterAdsConstants.PARAM_COUNTRY_CODE;
import static twitter4jads.TwitterAdsConstants.PARAM_CURSOR;
import static twitter4jads.TwitterAdsConstants.PARAM_CUSTOM_AUDIENCES;
import static twitter4jads.TwitterAdsConstants.PARAM_CUSTOM_AUDIENCES_EXCLUDED;
import static twitter4jads.TwitterAdsConstants.PARAM_CUSTOM_AUDIENCES_EXPANDED;
import static twitter4jads.TwitterAdsConstants.PARAM_DEVICES;
import static twitter4jads.TwitterAdsConstants.PARAM_ENGAGEMENT_TYPE;
import static twitter4jads.TwitterAdsConstants.PARAM_EVENT;
import static twitter4jads.TwitterAdsConstants.PARAM_EXACT_KEYWORDS;
import static twitter4jads.TwitterAdsConstants.PARAM_EXCLUDE_APP_LIST_IDENTIFIER;
import static twitter4jads.TwitterAdsConstants.PARAM_FOLLOWERS_OF_USERS;
import static twitter4jads.TwitterAdsConstants.PARAM_GENDER;
import static twitter4jads.TwitterAdsConstants.PARAM_IGNORED_VALUES;
import static twitter4jads.TwitterAdsConstants.PARAM_INTERESTS;
import static twitter4jads.TwitterAdsConstants.PARAM_LANGUAGES;
import static twitter4jads.TwitterAdsConstants.PARAM_LINE_ITEM_ID;
import static twitter4jads.TwitterAdsConstants.PARAM_LINE_ITEM_IDS;
import static twitter4jads.TwitterAdsConstants.PARAM_LOCATIONS;
import static twitter4jads.TwitterAdsConstants.PARAM_LOCATION_TYPE;
import static twitter4jads.TwitterAdsConstants.PARAM_NAME;
import static twitter4jads.TwitterAdsConstants.PARAM_NEGATIVE_BEHAVIOR;
import static twitter4jads.TwitterAdsConstants.PARAM_NEGATIVE_EXACT_KEYWORDS;
import static twitter4jads.TwitterAdsConstants.PARAM_NEGATIVE_PHRASE_KEYWORDS;
import static twitter4jads.TwitterAdsConstants.PARAM_NEGATIVE_UNORDERED_KEYWORDS;
import static twitter4jads.TwitterAdsConstants.PARAM_NETWORK_ACTIVATION_DURATION_GTE;
import static twitter4jads.TwitterAdsConstants.PARAM_NETWORK_ACTIVATION_DURATION_LT;
import static twitter4jads.TwitterAdsConstants.PARAM_NETWORK_OPERATOR;
import static twitter4jads.TwitterAdsConstants.PARAM_OPERATOR_TYPE;
import static twitter4jads.TwitterAdsConstants.PARAM_PHRASE_KEYWORDS;
import static twitter4jads.TwitterAdsConstants.PARAM_PLATFORMS;
import static twitter4jads.TwitterAdsConstants.PARAM_PLATFORM_VERSIONS;
import static twitter4jads.TwitterAdsConstants.PARAM_Q;
import static twitter4jads.TwitterAdsConstants.PARAM_SIMILAR_TO_FOLLOWERS_OF_USERS;
import static twitter4jads.TwitterAdsConstants.PARAM_SUGGESTION_TYPE;
import static twitter4jads.TwitterAdsConstants.PARAM_TARGETING_TYPE;
import static twitter4jads.TwitterAdsConstants.PARAM_TARGETING_VALUE;
import static twitter4jads.TwitterAdsConstants.PARAM_TARGETING_VALUES;
import static twitter4jads.TwitterAdsConstants.PARAM_TV_CHANNEL;
import static twitter4jads.TwitterAdsConstants.PARAM_TV_GENRE;
import static twitter4jads.TwitterAdsConstants.PARAM_TV_MARKET;
import static twitter4jads.TwitterAdsConstants.PARAM_TV_MARKET_LOCALE;
import static twitter4jads.TwitterAdsConstants.PARAM_TV_SHOWS;
import static twitter4jads.TwitterAdsConstants.PARAM_UNORDERED_KEYWORDS;
import static twitter4jads.TwitterAdsConstants.PARAM_USER_ENGAGEMENT;
import static twitter4jads.TwitterAdsConstants.PARAM_WIFI_ONLY;
import static twitter4jads.TwitterAdsConstants.PARAM_WITH_DELETED;
import static twitter4jads.TwitterAdsConstants.PATH_APP_LIST;
import static twitter4jads.TwitterAdsConstants.PATH_AUDIENCE_ESTIMATE;
import static twitter4jads.TwitterAdsConstants.PATH_BEHAVIORS;
import static twitter4jads.TwitterAdsConstants.PATH_BEHAVIORS_TAXONOMY;
import static twitter4jads.TwitterAdsConstants.PATH_IAB_CATEGORIES;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA_APP_STORE_CATEGORIES;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA_CONVERSATIONS;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA_EVENT;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA_INTERESTS;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA_LOCATION;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA_NETWORK_OPERATORS;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_CRITERIA_PLATFORMS;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_LANGUAGES;
import static twitter4jads.TwitterAdsConstants.PATH_TARGETING_SUGGESTIONS;
import static twitter4jads.TwitterAdsConstants.PATH_TV_CHANNELS;
import static twitter4jads.TwitterAdsConstants.PATH_TV_GENRES;
import static twitter4jads.TwitterAdsConstants.PATH_TV_MARKETS;
import static twitter4jads.TwitterAdsConstants.PATH_TV_SHOWS;
import static twitter4jads.TwitterAdsConstants.PREFIX_ACCOUNTS_URI_5;
import static twitter4jads.TwitterAdsConstants.PREFIX_BATCH_ACCOUNTS_V5;
import static twitter4jads.util.TwitterAdUtil.constructBaseAdsListResponse;

/**
 * User: abhay
 * Date: 4/4/16
 * Time: 7:16 PM
 */
public class TwitterAdsTargetingApiImpl implements TwitterAdsTargetingApi {

    private static final Integer MAX_REQUEST_PARAMETER_SIZE = 50;

    private static final Gson GSON = new Gson();
    private final TwitterAdsClient twitterAdsClient;

    public TwitterAdsTargetingApiImpl(TwitterAdsClient twitterAdsClient) {
        this.twitterAdsClient = twitterAdsClient;
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getTargetingCriterias(String accountId, String lineItemId, boolean withDeleted)
        throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "accountId");

        final List<HttpParameter> params = new ArrayList<>();
        if (TwitterAdUtil.isNotNullOrEmpty(lineItemId)) {
            // TODO: Add support for multiple lineItem Ids as added in Twitter Ads API v5
            params.add(new HttpParameter(PARAM_LINE_ITEM_IDS, lineItemId));
        }
        params.add(new HttpParameter(PARAM_WITH_DELETED, withDeleted));

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_TARGETING_CRITERIA;
        final Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
        }.getType();
        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsResponse<TargetingCriteria> getTargetingCriteriaById(String accountId, String targetingId, boolean withDeleted)
        throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(targetingId, "Targeting Id");

        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + TwitterAdsConstants.PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_TARGETING_CRITERIA +
                targetingId;
        HttpParameter[] params = new HttpParameter[]{new HttpParameter(PARAM_WITH_DELETED, withDeleted)};
        Type type = new TypeToken<BaseAdsResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpRequest(baseUrl, params, type, HttpVerb.GET);
    }

    @Override
    public BaseAdsResponse<TargetingCriteria> deleteTargetingCriteria(String accountId, String targetingCriteriaId) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(targetingCriteriaId, "Targeting Id");
        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + TwitterAdsConstants.PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_TARGETING_CRITERIA +
                targetingCriteriaId;
        Type type = new TypeToken<BaseAdsResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpRequest(baseUrl, null, type, HttpVerb.DELETE);
    }

    @Override
    public BaseAdsListResponseIterable<twitter4jads.models.ads.TargetingLocation> getAllTargetingLocations(Optional<LocationType> locationType, String q,
                                                                                                        String countryCode, Optional<Integer> count)
        throws TwitterException {
        List<HttpParameter> params = validateTargetingLocationParameters(locationType, q, countryCode, count);
        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TARGETING_CRITERIA_LOCATION;

        Type type = new TypeToken<BaseAdsListResponse<twitter4jads.models.ads.TargetingLocation>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTargetingEvents(String q) throws TwitterException {
        return hitQueryForPath(q, PATH_TARGETING_CRITERIA_EVENT);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTargetingInterests(String q) throws TwitterException {
        return hitQueryForPath(q, PATH_TARGETING_CRITERIA_INTERESTS);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTargetingPlatforms(String q) throws TwitterException {
        return hitQueryForPath(q, PATH_TARGETING_CRITERIA_PLATFORMS);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTargetingNetworkOperators(String q) throws TwitterException {
        return hitQueryForPath(q, PATH_TARGETING_CRITERIA_NETWORK_OPERATORS);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTargetingLocales(String q) throws TwitterException {
        return hitQueryForPath(q, PATH_TARGETING_LANGUAGES);
    }

    @Override
    public List<TargetingCriteria> createTargetingCriterias(String accountId, String lineItemId, List<TargetingCriteria> targetingCriteriaValues)
        throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemId, "Line Item");

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_TARGETING_CRITERIA;
        final List<HttpParameter> params = validateAndCreateTargetingParameters(targetingCriteriaValues);
        params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));

        final HttpResponse httpResponse = twitterAdsClient.putRequest(baseUrl, params.toArray(new HttpParameter[params.size()]));
        try {
            final Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
            }.getType();

            final String asString = httpResponse.asString();
            final BaseAdsListResponse<TargetingCriteria> baseAdsListResponse = constructBaseAdsListResponse(httpResponse, asString, type);
            return baseAdsListResponse == null ? Collections.<TargetingCriteria>emptyList() : baseAdsListResponse.getData();
        } catch (IOException e) {
            throw new TwitterException("Failed to parse targeting criterias.");
        }
    }

    @Override
    public BaseAdsResponse<TargetingCriteria> createTargetingCriteria(String accountId, String lineItemId, TargetingType targetingType,
            String targetingValue, OperatorType operatorType) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "AccountId");
        TwitterAdUtil.ensureNotNull(lineItemId, "Line Item Id");
        TwitterAdUtil.ensureNotNull(targetingType, "Targeting Type");
        TwitterAdUtil.ensureNotNull(targetingValue, "Targeting Value");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));
        params.add(new HttpParameter(PARAM_TARGETING_TYPE, targetingType.name()));
        params.add(new HttpParameter(PARAM_TARGETING_VALUE, targetingValue));
        params.add(new HttpParameter(PARAM_OPERATOR_TYPE, operatorType.name()));

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_TARGETING_CRITERIA;
        final Type type = new TypeToken<BaseAdsResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpRequest(baseUrl, params.toArray(new HttpParameter[params.size()]), type, HttpVerb.POST);
    }

    private void validateTargetingBatch(List<TargetingParamRequest> targetingParamRequests) throws TwitterException {
        if (!TwitterAdUtil.isNotEmpty(targetingParamRequests)) {
            throw new TwitterException("Targeting Params size is 0");
        }

        if (targetingParamRequests.size() > 500) {
            throw new TwitterException("Targeting Params size cannot be more than 500 as per the API");
            //https://dev.twitter.com/ads/reference/post/batch/accounts/%3Aaccount_id/targeting_criteria
        }
    }

    @Override
    public BaseAdsResponse<AudienceEstimate> getAudienceEstimate(String accountId, AudienceEstimateRequest audienceSummaryRequest)
        throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "AccountId");
        TwitterAdUtil.ensureNotNull(audienceSummaryRequest, "audienceSummaryRequest");

        final String url = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_AUDIENCE_ESTIMATE;
        final Type type = new TypeToken<BaseAdsResponse<AudienceEstimate>>() {
        }.getType();

        HttpResponse httpResponse = twitterAdsClient.postRequest(url, GSON.toJson(audienceSummaryRequest));
        return GSON.fromJson(httpResponse.asString(), type);
    }

    @Override
    public BaseAdsListResponseIterable<IabCategory> fetchIabCategories(String q) throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        if (TwitterAdUtil.isNotNullOrEmpty(q)) {
            params.add(new HttpParameter("q", q));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_IAB_CATEGORIES;
        final Type type = new TypeToken<BaseAdsListResponse<IabCategory>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public TargetingParamResponse createTargetingBatchRequest(String accountId, List<TargetingParamRequest> targetingParamRequests) throws TwitterException {
        validateTargetingBatch(targetingParamRequests);

        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_BATCH_ACCOUNTS_V5 + accountId
                + PATH_TARGETING_CRITERIA;
        HttpResponse httpResponse = twitterAdsClient.postBatchRequest(baseUrl, GSON.toJson(targetingParamRequests));
        Type typeToken = new TypeToken<TargetingParamResponse>() {
        }.getType();

        return GSON.fromJson(httpResponse.asString(), typeToken);
    }


    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTargetingTVChannels(String tvMarketLocale, Optional<Integer> count, Optional<String> cursor)
        throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        if (TwitterAdUtil.isNotNullOrEmpty(tvMarketLocale)) {
            params.add(new HttpParameter(PARAM_TV_MARKET_LOCALE, tvMarketLocale));
        }
        if (count != null && count.isPresent()) {
            params.add(new HttpParameter(PARAM_COUNT, count.get()));
        }
        if (cursor != null && cursor.isPresent()) {
            params.add(new HttpParameter(PARAM_CURSOR, cursor.get()));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TV_CHANNELS;
        Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingLocation> getTargetingLocations(String query, LocationType locationType) throws TwitterException {

        List<HttpParameter> params = new ArrayList<>(2);
        if (StringUtils.isNotBlank(query)) {
            params.add(new HttpParameter("q", query.trim()));
        }
        params.add(new HttpParameter("location_type", locationType.name()));
        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TARGETING_CRITERIA_LOCATION;
        Type type = new TypeToken<BaseAdsListResponse<TargetingLocation>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<Conversations> getAllTargetingConversations() throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TARGETING_CRITERIA_CONVERSATIONS;
        final Type type = new TypeToken<BaseAdsListResponse<Conversations>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<IabCategory> getAllIabCategories(String q) throws TwitterException {
        return hitQueryForPath(q, PATH_IAB_CATEGORIES);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTVShows(String tvMarket, String q, Optional<Integer> count, Optional<String> cursor)
        throws TwitterException {
        TwitterAdUtil.ensureNotNull(tvMarket, "tvMarket");

        List<HttpParameter> params = validateTvShowsParameters(tvMarket, q, count, cursor);
        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TV_SHOWS;

        final Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllEvents() throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TARGETING_CRITERIA_EVENT;
        final Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTVMarkets() throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TV_MARKETS;
        final Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<TargetingCriteria> getAllTargetingTVGenres() throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TV_GENRES;
        final Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public List<TargetingSuggestion> getTargetingSuggestion(String accountId, SuggestionType suggestionType, List<String> targetingValues,
                                                            Optional<Integer> count, List<String> ignoredValues) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "AccountId");
        TwitterAdUtil.ensureNotNull(suggestionType, "Suggestion Type");
        TwitterAdUtil.ensureNotEmpty(targetingValues, "Targeting Values");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_SUGGESTION_TYPE, suggestionType.name()));
        params.add(new HttpParameter(PARAM_TARGETING_VALUES, TwitterAdUtil.getCsv(targetingValues)));
        if (TwitterAdUtil.isNotEmpty(ignoredValues)) {
            params.add(new HttpParameter(PARAM_IGNORED_VALUES, TwitterAdUtil.getCsv(ignoredValues)));
        }
        if (count != null && count.isPresent()) {
            params.add(new HttpParameter(PARAM_COUNT, count.get() > MAX_REQUEST_PARAMETER_SIZE ? MAX_REQUEST_PARAMETER_SIZE : count.get()));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_TARGETING_SUGGESTIONS;
        final HttpResponse httpResponse = twitterAdsClient.getRequest(baseUrl, params.toArray(new HttpParameter[params.size()]));
        try {
            final Type type = new TypeToken<BaseAdsListResponse<TargetingSuggestion>>() {
            }.getType();

            final BaseAdsListResponse<TargetingSuggestion> baseAdsListResponse =
                constructBaseAdsListResponse(httpResponse, httpResponse.asString(), type);
            return baseAdsListResponse == null ? Collections.<TargetingSuggestion>emptyList() : baseAdsListResponse.getData();
        } catch (IOException e) {
            throw new TwitterException("Failed to parse promoted tweets.");
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<TwitterAppStore> searchAppStoreCategories(String q, Optional<AppStoreSearchType> appStoreSearchType) throws TwitterException {
        List<HttpParameter> params = new ArrayList<>();
        if (TwitterAdUtil.isNotNullOrEmpty(q)) {
            params.add(new HttpParameter("q", q));
        }
        if (appStoreSearchType != null && appStoreSearchType.isPresent()) {
            params.add(new HttpParameter("os_type", appStoreSearchType.get().name()));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_TARGETING_CRITERIA_APP_STORE_CATEGORIES;
        final HttpResponse httpResponse = twitterAdsClient.getRequest(baseUrl, params.toArray(new HttpParameter[params.size()]));
        try {
            final Type type = new TypeToken<BaseAdsListResponse<TwitterAppStore>>() {
            }.getType();

            final BaseAdsListResponse<TwitterAppStore> baseAdsListResponse =
                constructBaseAdsListResponse(httpResponse, httpResponse.asString(), type);
            return baseAdsListResponse == null ? Collections.<TwitterAppStore>emptyList() : baseAdsListResponse.getData();
        } catch (IOException e) {
            throw new TwitterException("Failed to parse response for app store categories");
        }
    }

    @Override
    public BaseAdsListResponseIterable<TwitterBehavior> getBehaviors(Optional<Integer> count, Optional<String> cursor,
                                                                     Optional<String> countryCode) throws TwitterException {
        List<HttpParameter> params = new ArrayList<>();
        if (count != null && count.isPresent()) {
            params.add(new HttpParameter("count", count.get()));
        }
        if (cursor != null && cursor.isPresent()) {
            params.add(new HttpParameter("cursor", cursor.get()));
        }
        if (countryCode != null && countryCode.isPresent()) {
            params.add(new HttpParameter("country_code", countryCode.get()));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_BEHAVIORS;
        final Type type = new TypeToken<BaseAdsListResponse<TwitterBehavior>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<TwitterBehaviorTaxonomy> getBehaviorTaxonomy(
            List<String> parentBehaviorTaxonomyIds, Optional<Integer> count,
                                                                                    Optional<String> cursor) throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();

        if (TwitterAdUtil.isNotEmpty(parentBehaviorTaxonomyIds)) {
            params.add(new HttpParameter("parent_behavior_taxonomy_ids", TwitterAdUtil.getCsv(parentBehaviorTaxonomyIds)));
        }

        if (count != null && count.isPresent()) {
            params.add(new HttpParameter("count", count.get()));
        }

        if (cursor != null && cursor.isPresent()) {
            params.add(new HttpParameter("cursor", cursor.get()));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PATH_BEHAVIORS_TAXONOMY;
        final Type type = new TypeToken<BaseAdsListResponse<TwitterBehaviorTaxonomy>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsListResponseIterable<TwitterApplicationList> getAllAppLists(String accountId) throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId + PATH_APP_LIST;
        final Type type = new TypeToken<BaseAdsListResponse<TwitterApplicationList>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsResponse<TwitterApplicationList> getAllAppsListsById(String accountId, String listId) throws TwitterException {
        //noinspection MismatchedQueryAndUpdateOfCollection
        final List<HttpParameter> params = new ArrayList<>();
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId + PATH_APP_LIST
                + listId;
        final Type typeToken = new TypeToken<BaseAdsResponse<TwitterApplicationList>>() {
        }.getType();

        return twitterAdsClient.executeHttpRequest(baseUrl, params.toArray(new HttpParameter[params.size()]), typeToken, HttpVerb.GET);
    }

    @Override
    public BaseAdsResponse<TwitterApplicationList> createNewApplicationList(String accountId, TwitterApplicationList twitterApplicationList)
        throws TwitterException {
        final List<HttpParameter> params = validateAndCreateApplicationListParameters(twitterApplicationList);
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId + PATH_APP_LIST;
        final Type typeToken = new TypeToken<BaseAdsResponse<TwitterApplicationList>>() {
        }.getType();

        return twitterAdsClient.executeHttpRequest(baseUrl, params.toArray(new HttpParameter[params.size()]), typeToken, HttpVerb.POST);
    }

    // ------------------------------------------------------------------- PRIVATE METHODS ---------------------------------------------------------

    @SuppressWarnings("Duplicates")
    private <T> BaseAdsListResponseIterable<T> hitQueryForPath(String q, String queryPath) throws TwitterException {
        final List<HttpParameter> params = new ArrayList<>();
        if (TwitterAdUtil.isNotNullOrEmpty(q)) {
            params.add(new HttpParameter("q", q));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + queryPath;
        final Type type = new TypeToken<BaseAdsListResponse<TargetingCriteria>>() {
        }.getType();

        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    private List<HttpParameter> validateAndCreateTargetingParameters(List<TargetingCriteria> targetingValues) {
        final List<HttpParameter> params = new ArrayList<>();
        final Map<TargetingType, List<String>> targeting = new HashMap<>();
        for (TargetingCriteria targetingCriteria : targetingValues) {
            if (targeting.containsKey(targetingCriteria.getTargetingType())) {
                targeting.get(targetingCriteria.getTargetingType()).add(targetingCriteria.getTargetingValue());
            } else {
                targeting.put(targetingCriteria.getTargetingType(), Lists.newArrayList(targetingCriteria.getTargetingValue()));
            }
        }

        for (Map.Entry<TargetingType, List<String>> entry : targeting.entrySet()) {
            switch (entry.getKey()) {
                case LOCATION:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 250);
                    params.add(new HttpParameter(PARAM_LOCATIONS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case FOLLOWERS_OF_USER:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 100);
                    params.add(new HttpParameter(PARAM_FOLLOWERS_OF_USERS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case SIMILAR_TO_FOLLOWERS_OF_USER:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 100);
                    params.add(new HttpParameter(PARAM_SIMILAR_TO_FOLLOWERS_OF_USERS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case INTEREST:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1000);
                    params.add(new HttpParameter(PARAM_INTERESTS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case PLATFORM:
                    params.add(new HttpParameter(PARAM_PLATFORMS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case PLATFORM_VERSION:
                    params.add(new HttpParameter(PARAM_PLATFORM_VERSIONS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case CONVERSATION:
                    params.add(new HttpParameter(PARAM_CONVERSATIONS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case DEVICE:
                    params.add(new HttpParameter(PARAM_DEVICES, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case WIFI_ONLY:
                    params.add(new HttpParameter(PARAM_WIFI_ONLY, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case GENDER:
                    params.add(new HttpParameter(PARAM_GENDER, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case TV_SHOW:
                    params.add(new HttpParameter(PARAM_TV_SHOWS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case TV_CHANNEL:
                    params.add(new HttpParameter(PARAM_TV_CHANNEL, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case TV_GENRE:
                    params.add(new HttpParameter(PARAM_TV_GENRE, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case TV_MARKET:
                    break;
                case NETWORK_OPERATOR:
                    params.add(new HttpParameter(PARAM_NETWORK_OPERATOR, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case BROAD_KEYWORD:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1000);
                    params.add(new HttpParameter(PARAM_BROAD_KEYWORDS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case UNORDERED_KEYWORD:
                    params.add(new HttpParameter(PARAM_UNORDERED_KEYWORDS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case PHRASE_KEYWORD:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1000);
                    params.add(new HttpParameter(PARAM_PHRASE_KEYWORDS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case EXACT_KEYWORD:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1000);
                    params.add(new HttpParameter(PARAM_EXACT_KEYWORDS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case NEGATIVE_PHRASE_KEYWORD:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1000);
                    params.add(new HttpParameter(PARAM_NEGATIVE_PHRASE_KEYWORDS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case NEGATIVE_UNORDERED_KEYWORD:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1000);
                    params.add(new HttpParameter(PARAM_NEGATIVE_UNORDERED_KEYWORDS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case NEGATIVE_EXACT_KEYWORD:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1000);
                    params.add(new HttpParameter(PARAM_NEGATIVE_EXACT_KEYWORDS, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case CUSTOM_AUDIENCE:
                    params.add(new HttpParameter(PARAM_CUSTOM_AUDIENCES, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case CUSTOM_AUDIENCES_EXCLUDED:
                    params.add(new HttpParameter(PARAM_CUSTOM_AUDIENCES_EXCLUDED, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case CUSTOM_AUDIENCES_EXPANDED:
                    params.add(new HttpParameter(PARAM_CUSTOM_AUDIENCES_EXPANDED, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case LANGUAGE:
                    params.add(new HttpParameter(PARAM_LANGUAGES, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case RTB_APP_CATEGORY:
                    break;
                case NETWORK_ACTIVATION_DURATION_LT:
                    params.add(new HttpParameter(PARAM_NETWORK_ACTIVATION_DURATION_LT, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case NETWORK_ACTIVATION_DURATION_GTE:
                    params.add(new HttpParameter(PARAM_NETWORK_ACTIVATION_DURATION_GTE, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case AGE:
                    params.add(new HttpParameter(PARAM_AGE_RANGE, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case BEHAVIOR:
                    params.add(new HttpParameter(PARAM_BEHAVIOR, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case NEGATIVE_BEHAVIOR:
                    params.add(new HttpParameter(PARAM_NEGATIVE_BEHAVIOR, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case BEHAVIOR_EXPANDED:
                    params.add(new HttpParameter(PARAM_BEHAVIOR_EXPANDED, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case ENGAGEMENT_TYPE:
                    params.add(new HttpParameter(PARAM_ENGAGEMENT_TYPE, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case USER_ENGAGEMENT:
                    params.add(new HttpParameter(PARAM_USER_ENGAGEMENT, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case CAMPAIGN_ENGAGEMENT:
                    params.add(new HttpParameter(PARAM_CAMPAIGN_ENGAGEMENT, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case EXCLUDE_APP_LIST:
                    params.add(new HttpParameter(PARAM_EXCLUDE_APP_LIST_IDENTIFIER, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case EVENT:
                    TwitterAdUtil.ensureMaxSize(entry.getValue(), 1);
                    params.add(new HttpParameter(PARAM_EVENT, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
                case APP_STORE_CATEGORY:
                    params.add(new HttpParameter(PARAM_APP_STORE_CATEGORY, TwitterAdUtil.getCsv(entry.getValue())));
                    break;
            }
        }
        return params;
    }

    private List<HttpParameter> validateTargetingLocationParameters(final Optional<LocationType> locationType, final String q, final String countryCode,
                                                                    final Optional<Integer> count) {

        List<HttpParameter> params = new ArrayList<>();
        if (locationType != null && locationType.isPresent()) {
            params.add(new HttpParameter(PARAM_LOCATION_TYPE, locationType.get().name()));
        }
        if (TwitterAdUtil.isNotNullOrEmpty(q)) {
            params.add(new HttpParameter(PARAM_Q, q));
        }
        if (TwitterAdUtil.isNotNullOrEmpty(countryCode)) {
            params.add(new HttpParameter(PARAM_COUNTRY_CODE, countryCode));
        }
        if (count != null && count.isPresent()) {
            params.add(new HttpParameter(PARAM_COUNT, count.get()));
        }
        return params;
    }

    private List<HttpParameter> validateTvShowsParameters(String tvMarket, String q, Optional<Integer> count, Optional<String> cursor) {
        TwitterAdUtil.ensureNotNull(tvMarket, "tvMarket");
        List<HttpParameter> params = new ArrayList<>();
        if (StringUtils.isNotBlank(tvMarket)) {
            params.add(new HttpParameter(PARAM_TV_MARKET, tvMarket));
        }
        if (StringUtils.isNotBlank(q)) {
            params.add(new HttpParameter(PARAM_Q, q));
        }
        if (count != null && count.isPresent()) {
            params.add(new HttpParameter(PARAM_COUNT, count.get()));
        }
        if (cursor != null && cursor.isPresent()) {
            params.add(new HttpParameter(PARAM_CURSOR, cursor.get()));
        }
        return params;
    }

    private List<HttpParameter> validateAndCreateApplicationListParameters(TwitterApplicationList twitterApplicationList) {
        List<HttpParameter> params = new ArrayList<>();
        if (TwitterAdUtil.isNotNullOrEmpty(twitterApplicationList.getName())) {
            params.add(new HttpParameter(PARAM_NAME, twitterApplicationList.getName()));
        }
        if (TwitterAdUtil.isNotEmpty(twitterApplicationList.getApps())) {
            List<TwitterApplicationDetails> apps = twitterApplicationList.getApps();
            List<String> appIdentifiers = Lists.newArrayList();
            for (TwitterApplicationDetails twitterApplicationDetails : apps) {
                if (TwitterAdUtil.isNotNullOrEmpty(twitterApplicationDetails.getAppIdentifier())) {
                    appIdentifiers.add(twitterApplicationDetails.getAppIdentifier());
                }
            }
            if (TwitterAdUtil.isNotEmpty(appIdentifiers)) {
                params.add(new HttpParameter(PARAM_APP_STORE_IDENTIFIERS, TwitterAdUtil.getCsv(appIdentifiers)));
            }
        }
        return params;
    }
}
