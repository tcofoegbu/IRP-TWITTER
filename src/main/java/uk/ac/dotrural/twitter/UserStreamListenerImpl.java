package uk.ac.dotrural.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import uk.ac.dotrural.entity.Message;
import uk.ac.dotrural.entity.TwitterDirectMessage;
import uk.ac.dotrural.entity.TwitterMessage;
import uk.ac.dotrural.util.EcoSystemBridge;
import uk.ac.dotrural.util.TweetFilter;

/**
 * 
 * @author charles
 *
 */
public class UserStreamListenerImpl implements UserStreamListener{
    
    private static Logger logger = LoggerFactory.getLogger(UserStreamListenerImpl.class);
    TwitterComponent twitterComponent = new TwitterComponent(this);    

    public void onStatus(Status tweet) {	
	if (TweetFilter.filterTweet(tweet)){
	    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> tweet was succesfully filterd...");
	    twitterComponent.followUser(tweet.getUser().getScreenName());	    
	    Message msg = new TwitterMessage(tweet);
	    EcoSystemBridge.processRequest(twitterComponent, msg);

	}	
    }
    
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {	
    }

    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {	
    }

    public void onScrubGeo(long userId, long upToStatusId) {	
    }

    public void onStallWarning(StallWarning warning) {	
    }

    public void onException(Exception ex) {	
    }

    public void onDeletionNotice(long directMessageId, long userId) {	
    }

    public void onFriendList(long[] friendIds) {	
    }

    public void onFavorite(User source, User target, Status favoritedStatus) {	
    }

    public void onUnfavorite(User source, User target, Status unfavoritedStatus) {	
    }

    public void onFollow(User source, User followedUser) {	
    }

    public void onDirectMessage(DirectMessage directMessage) {	
	if (TweetFilter.filterDM(directMessage)){
	    Message msg = new TwitterDirectMessage(directMessage);
	    EcoSystemBridge.processRequest(twitterComponent, msg);
	}
    }

    public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {	
    }

    public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {	
    }

    public void onUserListSubscription(User subscriber, User listOwner, UserList list) {	
    }

    public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {	
    }

    public void onUserListCreation(User listOwner, UserList list) {	
    }

    public void onUserListUpdate(User listOwner, UserList list) {	
    }

    public void onUserListDeletion(User listOwner, UserList list) {	
    }

    public void onUserProfileUpdate(User updatedUser) {	
    }

    public void onBlock(User source, User blockedUser) {	
    }
    
    public void onUnblock(User source, User unblockedUser) {	
    }


}
