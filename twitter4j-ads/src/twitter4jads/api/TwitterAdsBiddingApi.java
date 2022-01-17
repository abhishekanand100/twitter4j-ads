package twitter4jads.api;

import com.google.common.base.Optional;
import twitter4jads.internal.models4j.TwitterException;
import twitter4jads.models.ads.TwitterBidInfo;

/**
 * User: prashant
 * Date: 22/04/16.
 * Time: 2:49 PM
 */
public interface TwitterAdsBiddingApi {

    TwitterBidInfo getBidInfo(String accountId, String campaignType, Optional<String> currency, Optional<String> objectiveForBidding) throws TwitterException;

}
