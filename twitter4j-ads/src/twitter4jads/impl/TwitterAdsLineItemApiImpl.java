package twitter4jads.impl;

import com.google.common.base.Optional;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import twitter4jads.*;
import twitter4jads.api.TwitterAdsLineItemApi;
import twitter4jads.internal.http.HttpParameter;
import twitter4jads.internal.http.HttpResponse;
import twitter4jads.internal.models4j.TwitterException;
import twitter4jads.models.ads.*;
import twitter4jads.models.ads.sort.LineItemsSortByField;
import twitter4jads.models.ads.sort.PromotedAccountsSortByField;
import twitter4jads.models.media.TwitterMediaCallToAction;
import twitter4jads.models.video.AssociateMediaCreativeResponse;
import twitter4jads.models.video.TwitterCallToActionType;
import twitter4jads.util.TwitterAdUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static twitter4jads.TwitterAdsConstants.*;

/**
 * User: abhay
 * Date: 4/4/16
 * Time: 8:01 PM
 */
public class TwitterAdsLineItemApiImpl implements TwitterAdsLineItemApi {

    private static final Integer MAX_REQUEST_PARAMETER_SIZE = 200;

    private final TwitterAdsClient twitterAdsClient;

    public TwitterAdsLineItemApiImpl(TwitterAdsClient twitterAdsClient) {
        this.twitterAdsClient = twitterAdsClient;
    }

    @Override
    public BaseAdsResponse<LineItem> createLineItem(LineItem lineItem) throws TwitterException {
        final String accountId = lineItem.getAccountId();
        final String campaignId = lineItem.getCampaignId();

        Long bidAmountLocalMicro = null;
        BidStrategy bidStrategy = lineItem.getBidStrategy();
        if (bidStrategy == null) {
            bidStrategy = BidStrategy.AUTO;
        } else if (lineItem.getBidStrategy() != BidStrategy.AUTO) {
            bidAmountLocalMicro = lineItem.getBidAmtInMicro();
        }

        final Sentiments includeSentiment = lineItem.getSentiment();
        final Boolean matchRelevantPopularQueries = lineItem.getMatchRelevantPopularQueries();
        TwitterAdUtil.ensureNotNull(accountId, "accountId");

        final List<HttpParameter> params =
                validateCreateLineItemParameters(campaignId, bidStrategy, Optional.fromNullable(bidAmountLocalMicro),
                        lineItem.getProductType(), lineItem.getPlacements(), lineItem.getStatus(), lineItem.getObjective(),
                        Optional.fromNullable(lineItem.getPayBy()), Optional.fromNullable(lineItem.getAdvertiserDomain()),
                        lineItem.getCategories(), Optional.fromNullable(lineItem.getWebEventTag()), Optional.fromNullable(lineItem.getName()),
                        Optional.fromNullable(lineItem.getStartTime()), Optional.fromNullable(lineItem.getEndTime()),
                        lineItem.getTargetCpaLocalMicro(), Optional.fromNullable(lineItem.getTotalBudget()),
                        Optional.fromNullable(lineItem.getDailyBudget()), Optional.fromNullable(lineItem.getGoal()),
                        Optional.fromNullable(lineItem.getFrequencyCap()), Optional.fromNullable(lineItem.getDurationInDays()),
                        Optional.fromNullable(lineItem.getAudienceExpansion()), Optional.fromNullable(lineItem.getStandardDelivery()));
        HttpParameter[] parameters = null;
        if (!params.isEmpty()) {
            parameters = params.toArray(new HttpParameter[params.size()]);
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEMS;
        final HttpResponse httpResponse = twitterAdsClient.postRequest(baseUrl, parameters);
        try {
            Type type = new TypeToken<BaseAdsResponse<LineItem>>() {
            }.getType();
            return TwitterAdUtil.constructBaseAdsResponse(httpResponse, httpResponse.asString(), type);
        } catch (IOException e) {
            throw new TwitterException("Failed to parse line item.");
        }
    }

    @Override
    public BaseAdsResponse<LineItem> updateLineItem(String accountId, String lineItemId, Optional<BidStrategy> bidStrategy,
                                                    Optional<Long> bidAmountLocalMicro, Optional<EntityStatus> status,
                                                    Optional<String> payBy, Optional<AudienceExpansion> expansion,
                                                    Optional<String> advertiserDomain, Optional<String> goal,
                                                    String[] iabCategories, Optional<String> startTime, Optional<String> endTime,
                                                    Optional<String> name, Optional<Long> targetCPA, Optional<Long> totalBudget,
                                                    Optional<Long> dailyBudget, Optional<Integer> frequencyCap,
                                                    Optional<Integer> durationInDays, Optional<Boolean> standardDelivery) throws TwitterException {
        if (!bidStrategy.isPresent()) {
            bidAmountLocalMicro = null;
            bidStrategy = Optional.of(BidStrategy.AUTO);
        }

        final List<HttpParameter> params =
                validateUpdateLineItemParameters(accountId, lineItemId, bidStrategy, bidAmountLocalMicro, status,
                        payBy, expansion, advertiserDomain, goal, iabCategories, startTime, endTime, name, targetCPA,
                        totalBudget, dailyBudget, frequencyCap, durationInDays, standardDelivery);
        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + TwitterAdsConstants.PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEMS +
                lineItemId;
        Type type = new TypeToken<BaseAdsResponse<LineItem>>() {
        }.getType();

        return twitterAdsClient.executeHttpRequest(baseUrl, params.toArray(new HttpParameter[params.size()]), type, HttpVerb.PUT);
    }

    @Override
    public BaseAdsResponse<LineItem> deleteLineItem(String accountId, String lineItemId) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemId, "LineItem Id");

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId + PATH_LINE_ITEMS
                + lineItemId;
        final Type type = new TypeToken<BaseAdsResponse<LineItem>>() {
        }.getType();
        return twitterAdsClient.executeHttpRequest(baseUrl, null, type, HttpVerb.DELETE);
    }

    @Override
    public BaseAdsListResponseIterable<LineItem> getAllLineItems(String accountId, Optional<Collection<String>> campaignIds, Optional<Collection<String>> lineItemIds,
                                                                 Optional<Collection<String>> fundingInstrumentIds, Optional<Integer> count, boolean withDeleted,
                                                                 String cursor, Optional<LineItemsSortByField> sortByField) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "accountId");
        String campaignIdsAsString = null;
        String lineItemIdsAsString = null;
        String fundingInstrumentIdsAsString = null;
        if (campaignIds != null && campaignIds.isPresent()) {
            TwitterAdUtil.ensureMaxSize(campaignIds.get(), MAX_REQUEST_PARAMETER_SIZE);
            campaignIdsAsString = TwitterAdUtil.getCsv(campaignIds.get());
        }
        if (lineItemIds != null && lineItemIds.isPresent()) {
            TwitterAdUtil.ensureMaxSize(lineItemIds.get(), MAX_REQUEST_PARAMETER_SIZE);
            lineItemIdsAsString = TwitterAdUtil.getCsv(lineItemIds.get());
        }
        if (lineItemIds != null && fundingInstrumentIds.isPresent()) {
            TwitterAdUtil.ensureMaxSize(fundingInstrumentIds.get(), MAX_REQUEST_PARAMETER_SIZE);
            fundingInstrumentIdsAsString = TwitterAdUtil.getCsv(fundingInstrumentIds.get());
        }

        final List<HttpParameter> params =
                validateLineItemParameters(accountId, campaignIdsAsString, lineItemIdsAsString, fundingInstrumentIdsAsString, count, withDeleted,
                        cursor);
        if (sortByField != null && sortByField.isPresent()) {
            params.add(new HttpParameter(PARAM_SORT_BY, sortByField.get().getField()));
        }
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEMS;
        final Type type = new TypeToken<BaseAdsListResponse<LineItem>>() {
        }.getType();
        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsResponse<LineItem> getLineItemById(String accountId, String lineItemId, boolean withDeleted) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "accountId");
        TwitterAdUtil.ensureNotNull(lineItemId, "lineItemId");
        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + TwitterAdsConstants.PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEMS + lineItemId;
        HttpParameter[] params = new HttpParameter[]{new HttpParameter(PARAM_WITH_DELETED, withDeleted)};
        Type type = new TypeToken<BaseAdsResponse<LineItem>>() {
        }.getType();
        return twitterAdsClient.executeHttpRequest(baseUrl, params, type, HttpVerb.GET);
    }

    @Override
    public BaseAdsResponse<PromotedAccount> createPromotedAccounts(String accountId, String lineItemId, String userId) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemId, "Line Item Id");
        TwitterAdUtil.ensureNotNull(userId, "User Id");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));
        params.add(new HttpParameter(PARAM_USER_ID, userId));

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_PROMOTED_ACCOUNTS;
        final HttpResponse httpResponse = twitterAdsClient.postRequest(baseUrl, params.toArray(new HttpParameter[params.size()]));
        try {
            final Type type = new TypeToken<BaseAdsResponse<PromotedAccount>>() {
            }.getType();
            return TwitterAdUtil.constructBaseAdsResponse(httpResponse, httpResponse.asString(), type);
        } catch (IOException e) {
            throw new TwitterException("Failed to parse promoted accounts.");
        }
    }

    @Override
    public BaseAdsListResponseIterable<PromotedAccount> getPromotedAccounts(String accountId, Optional<Collection<String>> promotedAccountIds,
                                                                            Optional<String> lineItemId, boolean withDeleted,
                                                                            PromotedAccountsSortByField sortByField) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "accountId");
        String promotedAccountsIdsAsString = null;
        List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_WITH_DELETED, withDeleted));
        if (promotedAccountIds != null && promotedAccountIds.isPresent()) {
            TwitterAdUtil.ensureMaxSize(promotedAccountIds.get(), MAX_REQUEST_PARAMETER_SIZE);
            promotedAccountsIdsAsString = TwitterAdUtil.getCsv(promotedAccountIds.get());
        }
        if (lineItemId != null && lineItemId.isPresent()) {
            params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId.get()));
        }

        if (StringUtils.isNotBlank(promotedAccountsIdsAsString)) {
            params.add(new HttpParameter(PARAM_PROMOTED_ACCOUNTS_IDS, promotedAccountsIdsAsString));
        }
        String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + TwitterAdsConstants.PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_PROMOTED_ACCOUNTS;
        Type type = new TypeToken<BaseAdsListResponse<PromotedAccount>>() {
        }.getType();
        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsResponse<TwitterMediaCallToAction> createCallToActionDetails(String accountId, String lineItemId,
                                                                               TwitterCallToActionType twitterCallToActionType,
                                                                               String callToActionUrl) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemId, "Line Item Id");
        TwitterAdUtil.ensureNotNull(callToActionUrl, "Call To Action Url");
        TwitterAdUtil.ensureNotNull(twitterCallToActionType, "Call To Action Type");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));
        params.add(new HttpParameter(PARAM_ACCOUNT_ID, accountId));
        params.add(new HttpParameter(PARAM_CALL_TO_ACTION, twitterCallToActionType.name()));
        params.add(new HttpParameter(PARAM_CALL_TO_ACTION_URL, callToActionUrl));

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PRE_ROLL_CALL_TO_ACTION;
        final HttpResponse httpResponse = twitterAdsClient.postRequest(baseUrl, params.toArray(new HttpParameter[params.size()]));
        try {
            final Type type = new TypeToken<BaseAdsResponse<TwitterMediaCallToAction>>() {
            }.getType();
            return TwitterAdUtil.constructBaseAdsResponse(httpResponse, httpResponse.asString(), type);
        } catch (IOException e) {
            throw new TwitterException("Failed to parse call to action response.");
        }
    }

    //landing url is the url of the media creative
    @Override
    public BaseAdsResponse<AssociateMediaCreativeResponse> associateMediaCreativeWithAccount(String accountId, String lineItemId,
                                                                                             String accountMediaId, String landingUrl)
            throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemId, "Line Item Id");
        TwitterAdUtil.ensureNotNull(accountMediaId, "Account Media Id");
        TwitterAdUtil.ensureNotNull(landingUrl, " Landing Url");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_ACCOUNT_ID, accountId));
        params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));
        params.add(new HttpParameter(PARAM_ACCOUNT_MEDIA_ID, accountMediaId));
        if (TwitterAdUtil.isNotNullOrEmpty(landingUrl)) {
            params.add(new HttpParameter(PARAM_LANDING_URL, landingUrl));
        }
        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_MEDIA_CREATIVES;
        final HttpResponse httpResponse = twitterAdsClient.postRequest(baseUrl, params.toArray(new HttpParameter[params.size()]));
        try {
            final Type type = new TypeToken<BaseAdsResponse<AssociateMediaCreativeResponse>>() {
            }.getType();
            return TwitterAdUtil.constructBaseAdsResponse(httpResponse, httpResponse.asString(), type);
        } catch (IOException e) {
            throw new TwitterException("Failed to parse response for associate media to account", e);
        }
    }

    @Override
    public BaseAdsListResponseIterable<PromotedAccount> getPromotedAccounts(String accountId, Collection<String> promotedAccountIds, String lineItemId, boolean withDeleted) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "accountId");
        String promotedAccountsIdsAsString = null;

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_WITH_DELETED, withDeleted));
        if (TwitterAdUtil.isNotNull(promotedAccountIds)) {
            TwitterAdUtil.ensureMaxSize(promotedAccountIds, MAX_REQUEST_PARAMETER_SIZE);
            promotedAccountsIdsAsString = TwitterAdUtil.getCsv(promotedAccountIds);
        }
        if (TwitterAdUtil.isNotNullOrEmpty(lineItemId)) {
            params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));
        }

        if (TwitterAdUtil.isNotNullOrEmpty(promotedAccountsIdsAsString)) {
            params.add(new HttpParameter(PARAM_PROMOTED_ACCOUNTS_IDS, promotedAccountsIdsAsString));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_PROMOTED_ACCOUNTS;
        final Type type = new TypeToken<BaseAdsListResponse<PromotedAccount>>() {
        }.getType();
        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }


    @Override
    public BaseAdsResponse<TwitterMediaCallToAction> updateCallToAction(String accountId, String channelId, String callToActionUrl,
                                                                        TwitterCallToActionType twitterCallToActionType) throws TwitterException {

        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(channelId, "Channel Id");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_ID, channelId));
        if (StringUtils.isNotBlank(callToActionUrl)) {
            params.add(new HttpParameter(PARAM_CALL_TO_ACTION_URL, callToActionUrl));
        }
        if (twitterCallToActionType != null) {
            params.add(new HttpParameter(PARAM_CALL_TO_ACTION, twitterCallToActionType.name()));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PRE_ROLL_CALL_TO_ACTION + "/" + channelId;
        final HttpResponse httpResponse = twitterAdsClient.putRequest(baseUrl, params.toArray(new HttpParameter[params.size()]));
        try {
            final Type type = new TypeToken<BaseAdsResponse<TwitterMediaCallToAction>>() {
            }.getType();
            return TwitterAdUtil.constructBaseAdsResponse(httpResponse, httpResponse.asString(), type);
        } catch (IOException e) {
            throw new TwitterException("Failed to parse call to action response.");
        }
    }

    @Override
    public BaseAdsResponse<TwitterMediaCallToAction> deleteCallToAction(String accountId, String channelId) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(channelId, "Channel Id");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_ID, channelId));

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PRE_ROLL_CALL_TO_ACTION + "/" + channelId;
        final Type type = new TypeToken<BaseAdsResponse<TwitterMediaCallToAction>>() {
        }.getType();
        return twitterAdsClient.executeHttpRequest(baseUrl, params.toArray(new HttpParameter[params.size()]), type, HttpVerb.DELETE);
    }

    @Override
    public BaseAdsResponse<LineItemAppResponse> publishApp(String accountId, String lineItemId, String appStoreIdentifier,
                                                           TwitterOSType twitterOSType) throws TwitterException {

        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemId, "Line Item Id");
        TwitterAdUtil.ensureNotNull(appStoreIdentifier, "App Store Identifier");
        TwitterAdUtil.ensureNotNull(twitterOSType, "Twitter OS Type");

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_ACCOUNT_ID, accountId));
        params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));
        params.add(new HttpParameter(PARAM_APP_STORE_IDENTIFIER, appStoreIdentifier));
        params.add(new HttpParameter(PARAM_OS_TYPE, twitterOSType.name()));

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEM_APPS;
        final Type type = new TypeToken<BaseAdsResponse<LineItemAppResponse>>() {
        }.getType();
        return twitterAdsClient.executeHttpRequest(baseUrl, params.toArray(new HttpParameter[params.size()]), type, HttpVerb.POST);
    }

    @Override
    public BaseAdsResponse<LineItemAppResponse> getForLineItemAppId(String accountId, String lineItemAppId) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemAppId, "Line Item App Id");

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEM_APPS + "/" + lineItemAppId;
        final Type type = new TypeToken<BaseAdsResponse<LineItemAppResponse>>() {
        }.getType();
        return twitterAdsClient.executeHttpRequest(baseUrl, null, type, HttpVerb.GET);
    }

    @Override
    public BaseAdsListResponseIterable<LineItemAppResponse> getForLineItemAppIds(String accountId, String lineItemId, List<String> lineItemAppIds,
                                                                                 Integer count, String cursor, boolean withDeleted)
            throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        if (StringUtils.isBlank(lineItemId) && CollectionUtils.isEmpty(lineItemAppIds) && StringUtils.isBlank(cursor)) {
            throw new TwitterException("Details for fetching Apps are missing, either Line Item Id or App Ids or Cursor should be provided");
        }

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_ACCOUNT_ID, accountId));
        if (StringUtils.isNotBlank(lineItemId)) {
            params.add(new HttpParameter(PARAM_LINE_ITEM_ID, lineItemId));
        }
        if (StringUtils.isNotBlank(cursor)) {
            params.add(new HttpParameter(PARAM_CURSOR, cursor));
        }
        if (CollectionUtils.isNotEmpty(lineItemAppIds)) {
            params.add(new HttpParameter(PARAM_LINE_ITEM_APP_IDS, TwitterAdUtil.getCsv(lineItemAppIds)));
        }
        if (count != null) {
            params.add(new HttpParameter(PARAM_COUNT, count.toString()));
        }

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEM_APPS;
        final Type type = new TypeToken<BaseAdsListResponse<LineItemAppResponse>>() {
        }.getType();
        return twitterAdsClient.executeHttpListRequest(baseUrl, params, type);
    }

    @Override
    public BaseAdsResponse<LineItemAppResponse> deleteLineItemApp(String accountId, String lineItemAppId) throws TwitterException {
        TwitterAdUtil.ensureNotNull(accountId, "Account Id");
        TwitterAdUtil.ensureNotNull(lineItemAppId, "Line Item App Id");

        final String baseUrl = twitterAdsClient.getBaseAdsAPIUrl() + PREFIX_ACCOUNTS_URI_5 + accountId
                + PATH_LINE_ITEM_APPS + "/" + lineItemAppId;
        final Type type = new TypeToken<BaseAdsResponse<LineItemAppResponse>>() {
        }.getType();
        return twitterAdsClient.executeHttpRequest(baseUrl, null, type, HttpVerb.DELETE);
    }

    // -------------------------------------------------------------------- PRIVATE METHODS ---------------------------------------------------------

    private List<HttpParameter> validateCreateLineItemParameters(String campaignId,
                                                                 BidStrategy bidStrategy,
                                                                 Optional<Long> bidAmountLocalMicro,
                                                                 ProductType productType,
                                                                 List<Placement> placements,
                                                                 EntityStatus status,
                                                                 String objective,
                                                                 Optional<String> payBy,
                                                                 Optional<String> advertiserDomain,
                                                                 String[] categories,
                                                                 Optional<String> webEventTag,
                                                                 Optional<String> name,
                                                                 Optional<Date> startTime,
                                                                 Optional<Date> endTime,
                                                                 Long targetCpaLocalMicro,
                                                                 Optional<Long> totalBudget,
                                                                 Optional<Long> dailyBudget,
                                                                 Optional<String> goal,
                                                                 Optional<Integer> frequencyCap,
                                                                 Optional<Integer> durationInDays,
                                                                 Optional<AudienceExpansion> expansion,
                                                                 Optional<Boolean> standardDelivery) {
        TwitterAdUtil.ensureNotNull(campaignId, "CampaignId");
        TwitterAdUtil.ensureNotNull(startTime, "Start time");
        TwitterAdUtil.ensureNotNull(endTime, "End time");
        TwitterAdUtil.ensureNotNull(objective, "Objective");
        TwitterAdUtil.ensureNotEmpty(placements, "Placements");
        TwitterAdUtil.ensureNotNull(productType, "Product type");

        if (bidStrategy == BidStrategy.TARGET || bidStrategy == BidStrategy.MAX) {
            if (!bidAmountLocalMicro.isPresent()) {
                throw new IllegalArgumentException("Bid amount cannot be empty for TARGET or MAX Bid Type");
            }
        }

        final List<HttpParameter> params = new ArrayList<>();
        params.add(new HttpParameter(PARAM_CAMPAIGN_ID, campaignId));
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        if (TwitterAdUtil.isNotNull(startTime) && startTime.isPresent()) {
            final String formattedStartTime = df.format(startTime.get());
            params.add(new HttpParameter(PARAM_START_TIME, formattedStartTime));
        }

        if (TwitterAdUtil.isNotNull(endTime) && endTime.isPresent()) {
            String formattedEndTime = df.format(endTime.get());
            params.add(new HttpParameter(PARAM_END_TIME, formattedEndTime));
        }

        params.add(new HttpParameter(PARAM_OBJECTIVE, objective));

        // Twitter Audience Platform is supported for these objectives only
        if (TwitterAdUtil.ENGAGEMENTS.equals(objective) || TwitterAdUtil.VIDEO_VIEWS.equals(objective) ||
                TwitterAdUtil.WEBSITE_CLICKS.equals(objective)) {
            if (advertiserDomain != null && advertiserDomain.isPresent()) {
                params.add(new HttpParameter(PARAM_ADVERTISER_DOMAIN, advertiserDomain.get()));
            }
            if (categories != null && TwitterAdUtil.isNotEmpty(Arrays.asList(categories))) {
                params.add(new HttpParameter(PARAM_CATEGORIES, TwitterAdUtil.getCsv(Arrays.asList(categories))));
            }
        }

        if (TwitterAdUtil.TARGET_CPA_SUPPORTED_OBJECTIVES.contains(TwitterAdObjective.valueOf(objective))) {
            if (TwitterAdUtil.isNotNull(targetCpaLocalMicro)) {
                params.add(new HttpParameter(PARAM_TARGET_CPA_LOCAL_MICRO, targetCpaLocalMicro));
            }
        }

        params.add(new HttpParameter(PARAM_PLACEMENTS, TwitterAdUtil.getCsv(placements)));
        params.add(new HttpParameter(PARAM_PRODUCT_TYPE, productType.name()));
        params.add(new HttpParameter(PARAM_BID_STRATEGY, bidStrategy.name()));

        if (TwitterAdUtil.isNotNull(bidAmountLocalMicro) && bidAmountLocalMicro.isPresent()) {
            params.add(new HttpParameter(PARAM_BID_AMOUNT_LOCAL_MICRO, bidAmountLocalMicro.get()));
        }

        if (TwitterAdUtil.isNotNull(payBy) && payBy.isPresent()) {
            params.add(new HttpParameter(PARAM_PAY_BY, payBy.get()));
        }
        if (TwitterAdUtil.isNotNull(webEventTag) && webEventTag.isPresent()) {
            params.add(new HttpParameter(PARAM_PRIMARY_WEB_EVENT_TAG, webEventTag.get()));
        }
        if (TwitterAdUtil.isNotNull(name) && name.isPresent()) {
            params.add(new HttpParameter(PARAM_NAME, name.get()));
        }
        if (status != null) {
            params.add(new HttpParameter(PARAM_ENTITY_STATUS, status.name()));
        }

        if (TwitterAdUtil.isNotNull(goal) && goal.isPresent()) {
            params.add(new HttpParameter(PARAM_GOAL, goal.get()));
        }

        if (TwitterAdUtil.isNotNull(totalBudget) && totalBudget.isPresent()) {
            params.add(new HttpParameter(PARAM_TOTAL_BUDGET_AMOUNT_LOCAL_MICRO, totalBudget.get()));
        }
        if (TwitterAdUtil.isNotNull(dailyBudget) && dailyBudget.isPresent()) {
            params.add(new HttpParameter(PARAM_DAILY_BUDGET_AMOUNT_LOCAL_MICRO, dailyBudget.get()));
        }
        if (TwitterAdUtil.isNotNull(frequencyCap) && frequencyCap.isPresent()) {
            params.add(new HttpParameter(PARAM_FREQUENCY_CAP, frequencyCap.get()));
        }
        if (TwitterAdUtil.isNotNull(durationInDays) && durationInDays.isPresent()) {
            params.add(new HttpParameter(PARAM_DURATION_IN_DAYS, durationInDays.get()));
        }
        if (TwitterAdUtil.isNotNull(expansion) && expansion.isPresent()) {
            params.add(new HttpParameter(PARAM_AUDIENCE_EXPANSION, expansion.get().name()));
        }
        if (TwitterAdUtil.isNotNull(standardDelivery) && standardDelivery.isPresent()) {
            params.add(new HttpParameter(PARAM_STANDARD_DELIVERY, standardDelivery.toString()));
        }
        return params;
    }

    private List<HttpParameter> validateUpdateLineItemParameters(String accountId, String lineItemId, Optional<BidStrategy> bidStrategy,
                                                                 Optional<Long> bidAmountLocalMicro, Optional<EntityStatus> status,
                                                                 Optional<String> payBy, Optional<AudienceExpansion> expansion,
                                                                 Optional<String> advertiserDomain, Optional<String> goal,
                                                                 String[] iabCategories, Optional<String> startTime, Optional<String> endTime,
                                                                 Optional<String> name, Optional<Long> targetCPA, Optional<Long> totalBudget,
                                                                 Optional<Long> dailyBudget, Optional<Integer> frequencyCap,
                                                                 Optional<Integer> durationInDays, Optional<Boolean> standardDelivery) {
        TwitterAdUtil.ensureNotNull(accountId, "AccountId");
        TwitterAdUtil.ensureNotNull(lineItemId, "Line Item Id");

        final List<HttpParameter> params = new ArrayList<>();
        if (TwitterAdUtil.isNotNull(bidStrategy) && bidStrategy.isPresent()) {
            params.add(new HttpParameter(PARAM_BID_STRATEGY, bidStrategy.get().name()));
        }
        if (TwitterAdUtil.isNotNull(bidAmountLocalMicro) && bidAmountLocalMicro.isPresent()) {
            params.add(new HttpParameter(PARAM_BID_AMOUNT_LOCAL_MICRO, bidAmountLocalMicro.get()));
        }
        if (TwitterAdUtil.isNotNull(status) && status.isPresent()) {
            params.add(new HttpParameter(PARAM_ENTITY_STATUS, status.get().name()));
        }
        if (payBy != null && payBy.isPresent()) {
            params.add(new HttpParameter(PARAM_PAY_BY, payBy.get()));
        }
        if (expansion != null && expansion.isPresent()) {
            params.add(new HttpParameter(PARAM_AUDIENCE_EXPANSION, expansion.get().name()));
        }
        if (goal != null && goal.isPresent()) {
            params.add(new HttpParameter(PARAM_GOAL, goal.get()));
        }
        if (startTime != null && startTime.isPresent()) {
            params.add(new HttpParameter(PARAM_START_TIME, startTime.get()));
        }
        if (endTime != null && endTime.isPresent()) {
            params.add(new HttpParameter(PARAM_END_TIME, endTime.get()));
        }
        if (name != null && name.isPresent()) {
            params.add(new HttpParameter(PARAM_NAME, name.get()));
        }
        if (targetCPA != null && targetCPA.isPresent()) {
            params.add(new HttpParameter(PARAM_TARGET_CPA_LOCAL_MICRO, targetCPA.get()));
        }
        if (totalBudget != null && totalBudget.isPresent()) {
            params.add(new HttpParameter(PARAM_TOTAL_BUDGET_AMOUNT_LOCAL_MICRO, totalBudget.get()));
        }
        if (dailyBudget != null && dailyBudget.isPresent()) {
            params.add(new HttpParameter(PARAM_DAILY_BUDGET_AMOUNT_LOCAL_MICRO, dailyBudget.get()));
        }
        if (frequencyCap != null && frequencyCap.isPresent()) {
            params.add(new HttpParameter(PARAM_FREQUENCY_CAP, frequencyCap.get()));
        }
        if (durationInDays != null && durationInDays.isPresent()) {
            params.add(new HttpParameter(PARAM_DURATION_IN_DAYS, durationInDays.get()));
        }
        if (bidStrategy.equals(BidStrategy.AUTO)) {
            params.add(new HttpParameter(PARAM_BID_AMOUNT_LOCAL_MICRO, "null"));
        }
        if (TwitterAdUtil.isNotNull(standardDelivery) && standardDelivery.isPresent()) {
            params.add(new HttpParameter(PARAM_STANDARD_DELIVERY, standardDelivery.toString()));
        }

        // Twitter Audience Platform is supported for these objectives only

        if (advertiserDomain != null && advertiserDomain.isPresent()) {
            params.add(new HttpParameter(PARAM_ADVERTISER_DOMAIN, advertiserDomain.get()));
        }
        if (iabCategories != null && TwitterAdUtil.isNotEmpty(Arrays.asList(iabCategories))) {
            params.add(new HttpParameter(PARAM_CATEGORIES, TwitterAdUtil.getCsv(Arrays.asList(iabCategories))));
        }
        return params;
    }

    private List<HttpParameter> validateLineItemParameters(String accountId, String campaignIds, String lineItemIds, String fundingInstrumentIds,
                                                           Optional<Integer> count, boolean withDeleted, String cursor) {
        TwitterAdUtil.ensureNotNull(accountId, "accountId");
        List<HttpParameter> params = new ArrayList<>();
        if (StringUtils.isNotBlank(campaignIds)) {
            params.add(new HttpParameter(PARAM_CAMPAIGN_IDS, campaignIds));
        }
        if (StringUtils.isNotBlank(lineItemIds)) {
            params.add(new HttpParameter(PARAM_LINE_ITEM_IDS, lineItemIds));
        }
        if (StringUtils.isNotBlank(fundingInstrumentIds)) {
            params.add(new HttpParameter(PARAM_FUNDING_INSTRUMENT_IDS, fundingInstrumentIds));
        }
        params.add(new HttpParameter(PARAM_WITH_DELETED, withDeleted));
        if (count != null && count.isPresent()) {
            params.add(new HttpParameter(PARAM_COUNT, count.get()));
        }
        if (StringUtils.isNotBlank(cursor)) {
            params.add(new HttpParameter(PARAM_CURSOR, cursor));
        }
        return params;
    }
}
